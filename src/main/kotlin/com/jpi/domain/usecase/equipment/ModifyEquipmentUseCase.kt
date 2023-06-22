package com.jpi.domain.usecase.equipment

import com.jpi.domain.model.request.EquipmentRequest
import com.jpi.domain.model.request.ModifyEquipmentRequest
import com.jpi.domain.repository.EquipmentRepository

class ModifyEquipmentUseCase(private val repository: EquipmentRepository) {
    suspend operator fun invoke(productNumber: String, equipmentRequest: ModifyEquipmentRequest) = repository.modifyEquipment(productNumber, equipmentRequest)
}