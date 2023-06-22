package com.jpi.domain.usecase.order

import com.jpi.domain.repository.OrderRepository

class GetNoReturnUserListUseCase(private val orderRepository: OrderRepository) {
    suspend operator fun invoke() = orderRepository.getNoReturnUserList()
}