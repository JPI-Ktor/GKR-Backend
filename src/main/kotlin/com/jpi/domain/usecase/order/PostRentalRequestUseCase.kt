package com.jpi.domain.usecase.order

import com.jpi.domain.model.request.OrderRequest
import com.jpi.domain.repository.OrderRepository

class PostRentalRequestUseCase(private val orderRepository: OrderRepository) {
    suspend operator fun invoke(orderRequest: OrderRequest) = orderRepository.postRentalRequest(orderRequest)
}