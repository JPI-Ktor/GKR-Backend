package com.jpi.domain.model.request

import kotlinx.serialization.Serializable

@Serializable
data class ModifyEquipmentRequest(
    val name: String,
    val image: String,
    val description: String
)
