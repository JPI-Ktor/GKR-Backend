package com.jpi.domain.repository

import com.jpi.domain.model.request.OrderRequest
import com.jpi.domain.model.request.ExtensionRequest
import com.jpi.domain.model.response.EquipmentResponse
import com.jpi.domain.model.response.OrderResponse
import java.util.UUID

interface OrderRepository {
    suspend fun postRentalRequest(orderRequest: OrderRequest)

    suspend fun postReturnRequest(orderRequest: OrderRequest)

    suspend fun postExtensionRequest(extensionRequest: ExtensionRequest)

    suspend fun getRentalRequestList(): List<OrderResponse>

    suspend fun getWaitRequestList(): List<OrderResponse>

    suspend fun getRentalEquipment(userId: UUID): List<EquipmentResponse>

    suspend fun decideAcceptOrReject(orderRequest: OrderRequest, extensionRequest: ExtensionRequest)
}