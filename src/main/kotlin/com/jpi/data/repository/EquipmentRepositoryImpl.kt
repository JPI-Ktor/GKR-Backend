package com.jpi.data.repository

import com.jpi.data.model.response.asEquipmentResponse
import com.jpi.domain.entity.Equipment
import com.jpi.domain.model.request.EquipmentRequest
import com.jpi.domain.model.request.ModifyEquipmentRequest
import com.jpi.domain.model.response.EquipmentResponse
import com.jpi.domain.repository.EquipmentRepository
import com.jpi.domain.util.RentStatus
import com.jpi.server.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class EquipmentRepositoryImpl: EquipmentRepository {
    override suspend fun getAllEquipment(): List<EquipmentResponse> = dbQuery {
        Equipment.selectAll().map { it.asEquipmentResponse() }
    }

    override suspend fun getNotRentEquipmentList(): List<EquipmentResponse> = dbQuery {
        Equipment.select { Equipment.rentStatus eq RentStatus.NOT_RENT }
            .map { it.asEquipmentResponse() }
    }

    override suspend fun getIsRentEquipmentList(): List<EquipmentResponse> = dbQuery {
        Equipment.select { Equipment.rentStatus eq RentStatus.RENTING }
            .map { it.asEquipmentResponse() }
    }

    override suspend fun equipmentInfo(productNumber: String): EquipmentResponse? = dbQuery {
        Equipment.select { Equipment.productNumber eq productNumber }
            .map { it.asEquipmentResponse() }
            .singleOrNull()
    }

    override suspend fun addEquipment(equipmentRequest: EquipmentRequest) = dbQuery {
        val equipment = Equipment.select { Equipment.productNumber eq equipmentRequest.productNumber }
            .singleOrNull()

        if (equipment == null) {
            Equipment.insert {
                it[productNumber] = equipmentRequest.productNumber
                it[name] = equipmentRequest.name
                it[image] = equipmentRequest.image
                it[description] = equipmentRequest.description
                it[rentStatus] = RentStatus.NOT_RENT
            }
        }
    }

    override suspend fun modifyEquipment(productNumber: String, equipmentRequest: ModifyEquipmentRequest): Boolean = dbQuery {
        transaction {
            Equipment.update({ Equipment.productNumber eq productNumber }) {
                it[name] = equipmentRequest.name
                it[image] = equipmentRequest.image
                it[description] = equipmentRequest.description
                it[rentStatus] = rentStatus
            } > 0
        }
    }

    override suspend fun deleteEquipment(productNumber: String): Boolean = dbQuery {
        Equipment.deleteWhere { Equipment.productNumber eq productNumber } > 0
    }
}