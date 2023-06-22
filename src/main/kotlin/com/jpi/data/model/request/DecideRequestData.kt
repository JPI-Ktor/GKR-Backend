package com.jpi.data.model.request

import com.jpi.domain.model.request.DecideRequest
import com.jpi.domain.util.Decide
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class DecideRequestData(
    val equipmentId: String,
    val decision: Decide
)

fun DecideRequestData.asDecideRequest(userId: UUID) = DecideRequest(
    userId = userId,
    equipmentId = equipmentId,
    decision = decision
)