package com.jpi.data.repository

import com.jpi.data.model.response.asUserResponse
import com.jpi.domain.entity.RefreshToken
import com.jpi.domain.entity.User
import com.jpi.domain.model.request.GAuthSignInRequest
import com.jpi.domain.model.request.SignInRequest
import com.jpi.domain.model.response.GAuthTokenResponse
import com.jpi.domain.model.response.GAuthUserResponse
import com.jpi.domain.model.response.TokenResponse
import com.jpi.domain.repository.AuthRepository
import com.jpi.server.DatabaseFactory.dbQuery
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import java.time.LocalDateTime
import java.util.UUID

class AuthRepositoryImpl(private val client: HttpClient) : AuthRepository {
    override suspend fun signIn(signInRequest: SignInRequest): TokenResponse {
        val gAuthToken = client.post("https://server.gauth.co.kr/oauth/token") {
            contentType(ContentType.Application.Json)
            setBody(
                GAuthSignInRequest(
                    code = signInRequest.code,
                    clientSecret = System.getenv("CLIENT_SECRET"),
                    clientId = System.getenv("CLIENT_ID"),
                    redirectUri = System.getenv("REDIRECT_URI")
                )
            )
        }.body<GAuthTokenResponse>()

        val gAuthUserInfo = client.get("https://open.gauth.co.kr/user") {
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer ${gAuthToken.accessToken}")
        }.body<GAuthUserResponse>()

        val currentTime = LocalDateTime.now()
        val accessTokenExp = currentTime.plusMinutes(15)
        val refreshTokenExp = currentTime.plusDays(7)

        createUserOrRefreshToken(gAuthUserInfo = gAuthUserInfo, gAuthToken.refreshToken)

        return TokenResponse(
            accessToken = gAuthToken.accessToken,
            refreshToken = gAuthToken.refreshToken,
            accessTokenExp = accessTokenExp.toString(),
            refreshTokenExp = refreshTokenExp.toString()
        )
    }

    override suspend fun reissueToken(refreshToken: String): TokenResponse {
        val gAuthToken = client.patch("https://server.gauth.co.kr/oauth/token") {
            contentType(ContentType.Application.Json)
            header("refreshToken", "Bearer $refreshToken")
        }.body<GAuthTokenResponse>()

        val currentTime = LocalDateTime.now()
        val accessTokenExp = currentTime.plusMinutes(15)
        val refreshTokenExp = currentTime.plusDays(7)

        return TokenResponse(
            accessToken = gAuthToken.accessToken,
            refreshToken = gAuthToken.refreshToken,
            accessTokenExp = accessTokenExp.toString(),
            refreshTokenExp = refreshTokenExp.toString()
        )
    }

    override suspend fun getEmailByToken(accessToken: String): String {
        val gAuthUserInfo = client.get("https://open.gauth.co.kr/user") {
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer $accessToken")
        }.body<GAuthUserResponse>()

        return gAuthUserInfo.email
    }

    override suspend fun isTokenValid(accessToken: String): Boolean = dbQuery {
        val gAuthUserInfo = client.get("https://open.gauth.co.kr/user") {
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer $accessToken")
        }.body<GAuthUserResponse>()

        val uuid = User.select { User.email eq gAuthUserInfo.email }
            .map { it[User.id] }
            .single()

        val refreshToken = RefreshToken.select { RefreshToken.id eq uuid }
            .map { it[RefreshToken.refreshToken] }
            .single()

        refreshToken != ""
    }

    private suspend fun createUserOrRefreshToken(gAuthUserInfo: GAuthUserResponse, refreshToken: String) = dbQuery {
        val userInfo = User.select { User.email eq gAuthUserInfo.email }.map { it.asUserResponse() }.singleOrNull()
        val uuid = UUID.randomUUID()
        if (userInfo == null) {
            User.insert {
                it[id] = uuid
                it[email] = gAuthUserInfo.email
                it[name] = gAuthUserInfo.name
                it[grade] = gAuthUserInfo.grade
                it[classNum] = gAuthUserInfo.classNum
                it[number] = gAuthUserInfo.number
                it[profileUrl] = gAuthUserInfo.profileUrl
                it[role] = gAuthUserInfo.role
                it[isRentalRestricted] = false
            }
            RefreshToken.insert {
                it[id] = uuid
                it[RefreshToken.refreshToken] = refreshToken
            }
        } else {
            RefreshToken.update({RefreshToken.id eq userInfo.id }) {
               it[RefreshToken.refreshToken] = refreshToken
            }
        }
    }
}