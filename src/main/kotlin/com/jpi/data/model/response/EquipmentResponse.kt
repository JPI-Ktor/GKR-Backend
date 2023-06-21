package com.jpi.data.model.response

import com.jpi.domain.entity.Equipment
import com.jpi.domain.model.response.EquipmentResponse
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.asEquipmentResponse() = EquipmentResponse (
    productNumber = this[Equipment.productNumber],
    name = this[Equipment.name],
    image = this[Equipment.image],
    description = this[Equipment.description],
    numberLeft = this[Equipment.numberLeft],
    rentStatus = this[Equipment.rentStatus]
)