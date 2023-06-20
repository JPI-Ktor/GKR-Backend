package com.jpi.domain.repository

import com.jpi.domain.model.request.OrderRequest
import com.jpi.domain.model.response.OrderResponse

interface OrderRepository {
    suspend fun postRentalRequest(orderRequest: OrderRequest)

    suspend fun postReturnRequest(orderRequest: OrderRequest)

    suspend fun postExtensionRequest(orderRequest: OrderRequest)

    suspend fun getRentalRequestList(): List<OrderResponse>

    suspend fun getReturnRequestList(): List<OrderResponse>
}