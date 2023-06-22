package com.jpi.data.model.request

import com.jpi.domain.model.request.OrderRequest
import com.jpi.domain.util.Decide
import com.jpi.domain.util.State
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class OrderRequestData(
    val equipmentId: String,
    val reason: String,
    val state: State,
    val decision: Decide
)

fun OrderRequestData.asOrderRequest(userId: UUID) = OrderRequest(
    userId = userId,
    equipmentId = equipmentId,
    reason = reason,
    state = state,
    decision = decision
)