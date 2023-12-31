package com.jpi.domain.repository

import com.jpi.domain.model.response.UserResponse
import java.util.UUID

interface UserRepository {
    suspend fun getStudent(email: String): UserResponse?

    suspend fun getAllStudents(): List<UserResponse>

    suspend fun getUUID(accessToken: String): UUID?

    suspend fun logout(id: UUID)

    suspend fun isAdmin(accessToken: String): Boolean
}