package com.jpi.domain.usecase.equipment

import com.jpi.domain.repository.EquipmentRepository

class EquipmentFilterUseCase(private val repository: EquipmentRepository) {
    suspend operator fun invoke(propertyProductNumber: String) =
        repository.equipmentFilter(propertyProductNumber)
}