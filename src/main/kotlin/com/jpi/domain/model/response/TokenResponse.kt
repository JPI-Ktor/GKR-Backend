package com.jpi.domain.model.response

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExp: String,
    val refreshTokenExp: String
)
