package com.jpi.domain.usecase.auth

import com.jpi.domain.repository.AuthRepository

class ReissueTokenUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(refreshToken: String) = repository.reissueToken(refreshToken)
}