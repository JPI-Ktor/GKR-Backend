package com.jpi.domain.repository

import com.jpi.domain.model.request.EquipmentRequest
import com.jpi.domain.model.request.ModifyEquipmentRequest
import com.jpi.domain.model.response.EquipmentResponse

interface EquipmentRepository {
    suspend fun getAllEquipment(): List<EquipmentResponse>

    suspend fun getNotRentEquipmentList(): List<EquipmentResponse>

    suspend fun getIsRentEquipmentList(): List<EquipmentResponse>

    suspend fun equipmentInfo(productNumber: String): EquipmentResponse?

    suspend fun addEquipment(equipmentRequest: EquipmentRequest)

    suspend fun modifyEquipment(productNumber: String, equipmentRequest: ModifyEquipmentRequest): Boolean

    suspend fun deleteEquipment(productNumber: String): Boolean

    suspend fun equipmentFilter(name: String): List<EquipmentResponse>
}