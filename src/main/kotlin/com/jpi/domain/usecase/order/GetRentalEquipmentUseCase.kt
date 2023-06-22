package com.jpi.domain.usecase.order

import com.jpi.domain.repository.OrderRepository
import java.util.*

class GetRentalEquipmentUseCase(private val orderRepository: OrderRepository) {
    suspend operator fun invoke(userId: UUID) = orderRepository.getRentalEquipment(userId)
}