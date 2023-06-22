package com.jpi.data.model.request

import com.jpi.domain.model.request.ExtensionRequest
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ExtensionRequestData(
    val equipmentId: String
)

fun ExtensionRequestData.asExtensionRequest(userId: UUID) = ExtensionRequest(
    userId = userId,
    equipmentId = equipmentId
)