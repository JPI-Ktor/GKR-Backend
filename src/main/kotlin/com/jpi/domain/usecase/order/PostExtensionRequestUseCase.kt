package com.jpi.domain.usecase.order

import com.jpi.domain.model.request.ExtensionRequest
import com.jpi.domain.repository.OrderRepository

class PostExtensionRequestUseCase(private val orderRepository: OrderRepository) {
    suspend operator fun invoke(extensionRequest: ExtensionRequest) = orderRepository.postExtensionRequest(extensionRequest)
}