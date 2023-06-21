package com.jpi.data.repository

import com.jpi.data.model.response.asOrderResponse
import com.jpi.domain.State
import com.jpi.domain.entity.Order
import com.jpi.domain.model.request.ExtensionRequest
import com.jpi.domain.model.request.OrderRequest
import com.jpi.domain.model.response.OrderResponse
import com.jpi.domain.repository.OrderRepository
import com.jpi.server.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import java.time.LocalDateTime

class OrderRepositoryImpl: OrderRepository {
    override suspend fun postRentalRequest(orderRequest: OrderRequest): Unit = dbQuery {
        Order.insert {
            it[this.userId] = orderRequest.userId
            it[this.equipmentId] = orderRequest.equipmentId
            it[this.rentalReason] = orderRequest.reason
            it[this.rentalDate] = LocalDateTime.now()
            it[this.rentalState] = State.RENTAL_STATE
            it[this.returnDate] = LocalDateTime.now().plusMonths(1)
        }
    }

    override suspend fun postReturnRequest(orderRequest: OrderRequest): Unit = dbQuery {
        Order.update({ (Order.userId eq orderRequest.userId) and (Order.equipmentId eq orderRequest.equipmentId) }) {
            it[this.rentalState] = State.RETURN_STATE
            it[this.returnDate] = LocalDateTime.now()
        }
    }

    override suspend fun postExtensionRequest(extensionRequest: ExtensionRequest): Unit = dbQuery {
        val date = Order.select { (Order.userId eq extensionRequest.userId) and (Order.equipmentId eq extensionRequest.equipmentId) }.map { it[Order.returnDate] }.single()

        Order.update({ (Order.userId eq extensionRequest.userId) and (Order.equipmentId eq extensionRequest.equipmentId) }) {
            it[this.returnDate] = date.plusMonths(1)
        }
    }

    override suspend fun getRentalRequestList(): List<OrderResponse> = dbQuery {
        Order.select { Order.rentalState eq State.RENTAL_STATE }.map { it.asOrderResponse() }
    }

    override suspend fun getReturnRequestList(): List<OrderResponse> = dbQuery {
        Order.select { Order.rentalState eq State.RETURN_STATE }.map { it.asOrderResponse() }
    }
}