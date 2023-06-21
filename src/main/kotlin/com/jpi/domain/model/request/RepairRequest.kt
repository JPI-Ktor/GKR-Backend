package com.jpi.domain.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RepairRequest(
    val productNumber: String,
    val reason: String,
    val description: String,
    val repairDate: String,
    val cost: Int,
    val comment: String
)
