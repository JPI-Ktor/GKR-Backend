package com.jpi.domain.model.request

import com.jpi.domain.State
import com.jpi.domain.model.util.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class OrderRequest(
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
    val equipmentId: String,
    val reason: String,
    val state: State,
)
