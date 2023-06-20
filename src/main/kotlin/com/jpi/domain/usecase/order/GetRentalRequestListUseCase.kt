package com.jpi.domain.usecase.order

import com.jpi.domain.repository.OrderRepository

class GetRentalRequestListUseCase(private val orderRepository: OrderRepository) {
    suspend operator fun invoke() = orderRepository.getRentalRequestList()
}