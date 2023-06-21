package com.jpi.domain.usecase.equipment

import com.jpi.domain.repository.EquipmentRepository

class DeleteEquipmentUseCase(private val repository: EquipmentRepository) {
    suspend operator fun invoke(productNumber: String): Boolean = repository.deleteEquipment(productNumber)
}