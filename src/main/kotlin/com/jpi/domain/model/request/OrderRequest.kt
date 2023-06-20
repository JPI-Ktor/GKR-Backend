package com.jpi.domain.model.request

import com.jpi.domain.State
import kotlinx.serialization.Serializable

@Serializable
data class OrderRequest(
    val userId: String,
//    val id: String,
    val reason: String,
    val state: State
)
