package com.jpi.domain.usecase.repair

import com.jpi.domain.repository.RepairRepository

class DeleteRepairHistoryUseCase(private val repository: RepairRepository) {
    suspend operator fun invoke(productNumber: String) = repository.deleteRepairHistory(productNumber)
}