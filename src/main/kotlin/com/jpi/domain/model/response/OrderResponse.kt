package com.jpi.domain.model.response

import com.jpi.domain.State
import com.jpi.domain.model.util.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class OrderResponse(
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
//    val id: String,
    val reason: String,
    val state: State,
    val rentalDate: String,
    val returnDate: String
)
