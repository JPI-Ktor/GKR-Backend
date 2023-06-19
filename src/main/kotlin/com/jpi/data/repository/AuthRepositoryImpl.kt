package com.jpi.data.repository

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
import java.time.LocalDateTime

class AuthRepositoryImpl(private val client: HttpClient) : AuthRepository {
    override suspend fun signIn(signInRequest: SignInRequest): TokenResponse {
        val gAuthToken = client.post("https://server.gauth.co.kr/oauth/token") {
            contentType(ContentType.Application.Json)
            setBody(
                GAuthSignInRequest(
                    code = signInRequest.code,
                    clientId = "bc01ce725346420e94edd756071f6e1eaaed29c091014c01bd7296cb1c777ef3",
                    clientSecret = "8d81967d3c054d069ba069d65bef5a1f87af77066a8d4848832ffb8efebf86a9",
                    redirectUri = "http://localhost:8080"
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

        createUser(gAuthUserInfo = gAuthUserInfo)

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

    private suspend fun createUser(gAuthUserInfo: GAuthUserResponse) = dbQuery {
        val userInfo = User.select { User.email eq gAuthUserInfo.email }.singleOrNull()

        if (userInfo == null) {
            User.insert {
                it[email] = gAuthUserInfo.email
                it[name] = gAuthUserInfo.name
                it[grade] = gAuthUserInfo.grade
                it[classNum] = gAuthUserInfo.classNum
                it[number] = gAuthUserInfo.number
                it[profileUrl] = gAuthUserInfo.profileUrl
                it[role] = gAuthUserInfo.role
                it[isRentalRestricted] = false
            }
        }
    }
}