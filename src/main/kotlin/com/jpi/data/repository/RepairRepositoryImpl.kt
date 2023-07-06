package com.jpi.data.repository

import com.jpi.data.model.response.asRepairResponse
import com.jpi.domain.entity.Repair
import com.jpi.domain.model.request.RepairRequest
import com.jpi.domain.model.response.RepairResponse
import com.jpi.domain.repository.RepairRepository
import com.jpi.server.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import java.time.LocalDate
class RepairRepositoryImpl: RepairRepository {
    override suspend fun addRepairHistory(repairRequest: RepairRequest): Unit = dbQuery {
        Repair.insert {
            it[productNumber] = repairRequest.productNumber
            it[reason] = repairRequest.reason
            it[description] = repairRequest.description
            it[repairDate] = LocalDate.parse(repairRequest.repairDate)
            it[cost] = repairRequest.cost
            it[comment] = repairRequest.comment
        }
    }

    override suspend fun getRepairHistory(productNumber: String): List<RepairResponse> = dbQuery {
        Repair.select { Repair.productNumber eq productNumber }
            .map { it.asRepairResponse() }
    }

    override suspend fun modifyRepairHistory(repairRequest: RepairRequest): Unit = dbQuery {
        Repair.update({ Repair.productNumber eq repairRequest.productNumber }) {
            it[reason] = repairRequest.reason
            it[description] = repairRequest.description
            it[repairDate] = LocalDate.parse(repairRequest.repairDate)
            it[cost] = repairRequest.cost
            it[comment] = repairRequest.comment
        }
    }

    override suspend fun deleteRepairHistory(productNumber: String): Unit = dbQuery {
        Repair.deleteWhere { Repair.productNumber eq productNumber }
    }
}