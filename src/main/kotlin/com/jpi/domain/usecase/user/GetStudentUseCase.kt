package com.jpi.domain.usecase.user

import com.jpi.domain.repository.UserRepository

class GetStudentUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(email: String) = repository.getStudent(email)
}