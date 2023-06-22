package com.jpi.domain.usecase.order

import com.jpi.domain.model.request.DecideRequest
import com.jpi.domain.repository.OrderRepository

class DecideAcceptOrRejectUseCase(private val orderRepository: OrderRepository) {
    suspend operator fun invoke(decideRequest: DecideRequest) = orderRepository.decideAcceptOrReject(decideRequest)
}