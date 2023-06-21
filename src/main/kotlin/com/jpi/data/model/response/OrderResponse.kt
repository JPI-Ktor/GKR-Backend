package com.jpi.data.model.response

import com.jpi.domain.entity.Order
import com.jpi.domain.model.response.OrderResponse
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.asOrderResponse() = OrderResponse(
    userId = this[Order.userId],
    equipmentId = this[Order.equipmentId],
    reason = this[Order.rentalReason],
    state = this[Order.rentalState],
    rentalDate =  this[Order.rentalDate].toString(),
    returnDate = this[Order.returnDate].toString()
)