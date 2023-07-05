package com.jpi.domain.model.request

import com.jpi.domain.model.util.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ViolationRequest(
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
    val reason: String
)
