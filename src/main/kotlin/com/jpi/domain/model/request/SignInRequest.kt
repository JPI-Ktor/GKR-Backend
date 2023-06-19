package com.jpi.domain.model.request

import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest(
    val code: String
)
