package com.jpi.domain.model.request

import kotlinx.serialization.Serializable

@Serializable
data class EquipmentRequest(
    val productNumber: String,
    val name: String,
    val image: String,
    val description: String
)
