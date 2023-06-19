package com.jpi.data.repository

import com.jpi.data.model.response.asUserResponse
import com.jpi.domain.entity.User
import com.jpi.domain.model.response.UserResponse
import com.jpi.domain.repository.UserRepository
import com.jpi.server.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class UserRepositoryImpl: UserRepository {
    override suspend fun getStudent(email: String): UserResponse? = dbQuery {
        User.select { User.email eq email }
            .map { it.asUserResponse() }
            .singleOrNull()
    }

    override suspend fun getAllStudents(): List<UserResponse> = dbQuery {
        User.selectAll().map { it.asUserResponse() }
    }
}