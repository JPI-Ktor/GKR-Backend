package com.jpi.domain.usecase.auth

import com.jpi.domain.repository.AuthRepository

class IsTokenValidUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(accessToken: String) = repository.isTokenValid(accessToken)
}