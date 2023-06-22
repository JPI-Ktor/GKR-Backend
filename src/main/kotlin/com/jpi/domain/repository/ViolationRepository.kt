package com.jpi.domain.repository

import com.jpi.domain.model.request.ViolationRequest
import com.jpi.domain.model.response.ViolationResponse
import java.util.UUID

interface ViolationRepository {
    suspend fun rentalViolation(violationRequest: ViolationRequest)

    suspend fun getViolation(id: UUID): List<ViolationResponse>
}