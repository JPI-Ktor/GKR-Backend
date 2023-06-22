package com.jpi.domain.model.request

import com.jpi.domain.model.util.UUIDSerializer
import com.jpi.domain.util.Decide
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class DecideRequest(
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
    val equipmentId: String,
    val decision: Decide
)
