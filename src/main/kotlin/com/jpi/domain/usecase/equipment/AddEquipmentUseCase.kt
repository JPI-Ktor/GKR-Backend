package com.jpi.domain.usecase.equipment

import com.jpi.domain.model.request.EquipmentRequest
import com.jpi.domain.repository.EquipmentRepository

class AddEquipmentUseCase(private val repository: EquipmentRepository) {
    suspend operator fun invoke(equipmentRequest: EquipmentRequest) = repository.addEquipment(equipmentRequest)
}