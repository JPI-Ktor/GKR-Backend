package com.jpi.domain.model.request

import com.jpi.domain.model.util.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ExtensionRequest(
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
    val equipmentId: String
)
