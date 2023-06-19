package com.jpi.domain.repository

import com.jpi.domain.model.response.UserResponse
import java.util.UUID

interface UserRepository {
    suspend fun getStudent(email: String): UserResponse?

    suspend fun getAllStudents(): List<UserResponse>

    suspend fun restrictRental(id: UUID)
}