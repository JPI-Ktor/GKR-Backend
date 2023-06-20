package com.jpi.domain.usecase.user

import com.jpi.domain.repository.UserRepository

class IsAdminUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(accessToken: String) = repository.isAdmin(accessToken)
}