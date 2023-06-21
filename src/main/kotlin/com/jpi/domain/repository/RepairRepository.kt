package com.jpi.domain.repository

import com.jpi.domain.model.request.RepairRequest
import com.jpi.domain.model.response.RepairResponse

interface RepairRepository {
    suspend fun addRepairHistory(repairRequest: RepairRequest)

    suspend fun getRepairHistory(productNumber: String): RepairResponse?

    suspend fun modifyRepairHistory(repairRequest: RepairRequest)

    suspend fun deleteRepairHistory(productNumber: String)
}