package com.jpi.domain.usecase.repair

import com.jpi.domain.model.request.RepairRequest
import com.jpi.domain.repository.RepairRepository

class ModifyRepairHistoryUseCase(private val repository: RepairRepository) {
    suspend operator fun invoke(repairRequest: RepairRequest) = repository.modifyRepairHistory(repairRequest)
}