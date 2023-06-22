package com.jpi.domain.model.response

import com.jpi.domain.model.util.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ViolationResponse(
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
    val reason: String,
    val date: String
)
