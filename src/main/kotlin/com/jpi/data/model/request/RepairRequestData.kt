package com.jpi.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RepairRequestData(
    val productNumber: String
)
