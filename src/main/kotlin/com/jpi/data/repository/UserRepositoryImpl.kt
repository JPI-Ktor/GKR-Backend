package com.jpi.data.repository

import com.jpi.data.model.response.asUserResponse
import com.jpi.domain.entity.RefreshToken
import com.jpi.domain.entity.User
import com.jpi.domain.model.response.GAuthUserResponse
import com.jpi.domain.model.response.UserResponse
import com.jpi.domain.repository.UserRepository
import com.jpi.server.DatabaseFactory.dbQuery
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import java.util.*

class UserRepositoryImpl(private val client: HttpClient): UserRepository {
    override suspend fun getStudent(email: String): UserResponse? = dbQuery {
        User.select { User.email eq email }
            .map { it.asUserResponse() }
            .singleOrNull()
    }

    override suspend fun getAllStudents(): List<UserResponse> = dbQuery {
        User.selectAll().map { it.asUserResponse() }
    }

    override suspend fun restrictRental(id: UUID): Unit = dbQuery {
        User.update({User.id eq id}) {
            it[isRentalRestricted] = true
        }
    }

    override suspend fun getUUID(accessToken: String): UUID? = dbQuery {
        val gAuthUserInfo = client.get("https://open.gauth.co.kr/user") {
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer $accessToken")
        }.body<GAuthUserResponse>()

        User.select { User.email eq gAuthUserInfo.email }
            .map { it[User.id] }
            .singleOrNull()
    }

    override suspend fun logout(id: UUID): Unit = dbQuery {
        RefreshToken.update({RefreshToken.id eq id}) {
            it[refreshToken] = ""
        }
    }
}