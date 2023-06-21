package com.jpi.domain.usecase.equipment

import com.jpi.domain.repository.EquipmentRepository

class GetAllEquipmentUseCase(private val repository: EquipmentRepository) {
    suspend operator fun invoke() = repository.getAllEquipment()
}