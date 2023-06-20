package com.jpi.domain.usecase.order

import com.jpi.domain.repository.OrderRepository

class GetReturnRequestListUseCase(private val orderRepository: OrderRepository) {
    suspend operator fun invoke() = orderRepository.getReturnRequestList()
}