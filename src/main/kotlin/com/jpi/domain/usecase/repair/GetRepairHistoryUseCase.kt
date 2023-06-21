package com.jpi.domain.usecase.repair

import com.jpi.domain.repository.RepairRepository

class GetRepairHistoryUseCase(private val repository: RepairRepository) {
    suspend operator fun invoke(productNumber: String) = repository.getRepairHistory(productNumber)
}