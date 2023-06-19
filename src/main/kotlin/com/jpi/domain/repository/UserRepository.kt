package com.jpi.domain.repository

import com.jpi.domain.model.response.UserResponse

interface UserRepository {
    suspend fun getStudent(email: String): UserResponse?

    suspend fun getAllStudents(): List<UserResponse>
}