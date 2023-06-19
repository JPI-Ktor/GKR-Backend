package com.jpi.domain.usecase.auth

import com.jpi.domain.repository.AuthRepository

class GetEmailByTokenUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(accessToken: String) = repository.getEmailByToken(accessToken)
}