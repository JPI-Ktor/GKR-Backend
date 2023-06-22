package com.jpi.domain.usecase.violation

import com.jpi.domain.repository.ViolationRepository
import java.util.*

class GetViolationUseCase(private val repository: ViolationRepository) {
    suspend operator fun invoke(userId: UUID) = repository.getViolation(userId)
}