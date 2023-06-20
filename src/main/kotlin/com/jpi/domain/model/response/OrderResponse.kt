package com.jpi.domain.model.response

import com.jpi.domain.State

data class OrderResponse(
    val userId: String,
//    val id: String,
    val reason: String,
    val state: State
)
