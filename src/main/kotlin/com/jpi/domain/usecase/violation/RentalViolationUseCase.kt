package com.jpi.domain.usecase.violation

import com.jpi.domain.model.request.ViolationRequest
import com.jpi.domain.repository.ViolationRepository

class RentalViolationUseCase(private val repository: ViolationRepository) {
    suspend operator fun invoke(violationRequest: ViolationRequest) = repository.rentalViolation(violationRequest)
}