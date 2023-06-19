package com.jpi.domain.model.response

import kotlinx.serialization.Serializable

@Serializable
data class GAuthTokenResponse(
    val accessToken: String,
    val refreshToken: String
)
