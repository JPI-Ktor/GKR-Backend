package com.jpi.domain.repository

import com.jpi.domain.model.request.SignInRequest
import com.jpi.domain.model.response.TokenResponse

interface AuthRepository {
    suspend fun signIn(signInRequest: SignInRequest): TokenResponse

    suspend fun reissueToken(refreshToken: String): TokenResponse

    suspend fun getEmailByToken(accessToken: String): String

    suspend fun isTokenValid(accessToken: String): Boolean
}