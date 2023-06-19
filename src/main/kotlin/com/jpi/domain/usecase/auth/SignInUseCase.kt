package com.jpi.domain.usecase.auth

import com.jpi.domain.model.request.SignInRequest
import com.jpi.domain.repository.AuthRepository

class SignInUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(signInRequest: SignInRequest) = repository.signIn(signInRequest)
}