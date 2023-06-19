package com.jpi.domain.model.request

import kotlinx.serialization.Serializable

@Serializable
data class GAuthSignInRequest(
    val code: String,
    val clientId: String,
    val clientSecret: String,
    val redirectUri: String
)
