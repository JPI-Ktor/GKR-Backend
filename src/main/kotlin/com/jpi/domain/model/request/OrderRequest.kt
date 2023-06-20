package com.jpi.domain.model.request

import kotlinx.serialization.Serializable

@Serializable
data class OrderRequest(
    val userId: String,
//    val id: String,
    val reason: String
)
