package com.jpi.domain.model.response

import kotlinx.serialization.Serializable

@Serializable
data class RepairResponse(
    val productNumber: String,
    val reason: String,
    val description: String,
    val repairDate: String,
    val cost: Int,
    val comment: String
)
