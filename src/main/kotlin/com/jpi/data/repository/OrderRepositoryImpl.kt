package com.jpi.data.repository

import com.jpi.data.model.response.asEquipmentResponse
import com.jpi.data.model.response.asOrderResponse
import com.jpi.domain.entity.Equipment
import com.jpi.domain.util.State
import com.jpi.domain.entity.Order
import com.jpi.domain.model.request.ExtensionRequest
import com.jpi.domain.model.request.OrderRequest
import com.jpi.domain.model.response.EquipmentResponse
import com.jpi.domain.model.response.OrderResponse
import com.jpi.domain.repository.OrderRepository
import com.jpi.domain.util.Decide
import com.jpi.server.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.time.LocalDateTime
import java.util.UUID

class OrderRepositoryImpl: OrderRepository {
    override suspend fun postRentalRequest(orderRequest: OrderRequest): Unit = dbQuery {
        Order.insert {
            it[this.userId] = orderRequest.userId
            it[this.equipmentId] = orderRequest.equipmentId
            it[this.rentalReason] = orderRequest.reason
            it[this.rentalDate] = LocalDateTime.now()
            it[this.rentalState] = State.WAITING_STATE
            it[this.returnDate] = LocalDateTime.now().plusMonths(1)
        }
    }

    override suspend fun postReturnRequest(orderRequest: OrderRequest): Unit = dbQuery {
        Order.update({ (Order.userId eq orderRequest.userId) and (Order.equipmentId eq orderRequest.equipmentId) }) {
            it[this.rentalState] = State.WAITING_STATE
        }
    }

    override suspend fun postExtensionRequest(extensionRequest: ExtensionRequest): Unit = dbQuery {
        Order.update({ (Order.userId eq extensionRequest.userId) and (Order.equipmentId eq extensionRequest.equipmentId) }) {
            it[this.rentalState] = State.WAITING_STATE
        }
    }

    override suspend fun getRentalRequestList(): List<OrderResponse> = dbQuery {
        Order.select { Order.rentalState eq State.RENTAL_STATE }.map { it.asOrderResponse() }
    }

    override suspend fun getWaitRequestList(): List<OrderResponse> = dbQuery {
        Order.select { Order.rentalState eq State.WAITING_STATE }.map { it.asOrderResponse() }
    }

    override suspend fun getRentalEquipment(userId: UUID): List<EquipmentResponse> = dbQuery {
        val equipmentIdList = Order.select { Order.userId eq userId }.map { it[Order.equipmentId] }
        equipmentIdList.map { equipmentId ->
            Equipment.select { Equipment.productNumber eq equipmentId }.map { it.asEquipmentResponse() }.single()
        }
    }

    override suspend fun decideAcceptOrReject(orderRequest: OrderRequest, extensionRequest: ExtensionRequest): Unit = dbQuery {
        when(orderRequest.decision) {
            Decide.RENTAL_ACCEPT -> {
                Order.update({ (Order.userId eq orderRequest.userId) and (Order.equipmentId eq orderRequest.equipmentId) }) {
                    it[this.rentalState] = State.RENTAL_STATE
                    it[this.rentalDate] = LocalDateTime.now()
                }
            }
            Decide.RETURN_ACCEPT -> {
                Order.update({ (Order.userId eq orderRequest.userId) and (Order.equipmentId eq orderRequest.equipmentId) }) {
                    it[this.rentalState] = State.RETURN_STATE
                    it[this.returnDate] = LocalDateTime.now()
                }
            }
            Decide.EXTENSION_ACCEPT -> {
                val date = Order.select { (Order.userId eq extensionRequest.userId) and (Order.equipmentId eq extensionRequest.equipmentId) }.map { it[Order.returnDate] }.single()

                Order.update({ (Order.userId eq extensionRequest.userId) and (Order.equipmentId eq extensionRequest.equipmentId) }) {
                    it[this.rentalState] = State.RENTAL_STATE
                    it[this.returnDate] = date.plusMonths(1)
                }
            }
            Decide.REJECT -> {
                Order.deleteWhere { (this.userId eq orderRequest.userId) and (this.equipmentId eq orderRequest.equipmentId) }
            }
        }
    }
}