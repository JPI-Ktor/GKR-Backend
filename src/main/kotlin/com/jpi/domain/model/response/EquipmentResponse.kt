package com.jpi.domain.model.response

import com.jpi.domain.util.RentStatus
import kotlinx.serialization.Serializable

@Serializable
data class EquipmentResponse(
    val productNumber: String,
    val name: String,
    val image: String,
    val description: String,
    val rentStatus: RentStatus
)
