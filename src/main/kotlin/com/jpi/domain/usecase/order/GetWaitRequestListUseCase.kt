package com.jpi.domain.usecase.order

import com.jpi.domain.repository.OrderRepository

class GetWaitRequestListUseCase(private val orderRepository: OrderRepository) {
    suspend operator fun invoke() = orderRepository.getWaitRequestList()
}