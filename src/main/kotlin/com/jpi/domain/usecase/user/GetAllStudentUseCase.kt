package com.jpi.domain.usecase.user

import com.jpi.domain.repository.UserRepository

class GetAllStudentUseCase(private val repository: UserRepository) {
    suspend operator fun invoke() = repository.getAllStudents()
}