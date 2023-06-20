package com.jpi.domain.usecase.equipment

import com.jpi.domain.repository.EquipmentRepository

class EquipmentInfoUseCase(private val repository: EquipmentRepository) {
    suspend operator fun invoke(productNumber: String) = repository.equipmentInfo(productNumber)
}