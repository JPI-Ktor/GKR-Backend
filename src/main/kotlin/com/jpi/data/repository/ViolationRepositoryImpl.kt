package com.jpi.data.repository

import com.jpi.data.model.response.asViolationResponse
import com.jpi.domain.entity.Violation
import com.jpi.domain.model.request.ViolationRequest
import com.jpi.domain.model.response.ViolationResponse
import com.jpi.domain.repository.ViolationRepository
import org.jetbrains.exposed.sql.select
import com.jpi.server.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.insert
import java.time.LocalDate
import java.util.*

class ViolationRepositoryImpl: ViolationRepository {
    override suspend fun rentalViolation(violationRequest: ViolationRequest): Unit = dbQuery {
        Violation.insert {
            it[userId] = violationRequest.userId
            it[reason] = violationRequest.reason
            it[date] = LocalDate.now()
        }
    }

    override suspend fun getViolation(id: UUID): List<ViolationResponse> = dbQuery {
        Violation.select { Violation.userId eq id }.map { it.asViolationResponse() }
    }
}