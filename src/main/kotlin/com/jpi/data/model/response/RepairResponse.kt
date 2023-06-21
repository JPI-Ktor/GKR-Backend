package com.jpi.data.model.response

import com.jpi.domain.entity.Repair
import com.jpi.domain.model.response.RepairResponse
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.asRepairResponse() = RepairResponse(
    productNumber = this[Repair.productNumber],
    reason = this[Repair.reason],
    description = this[Repair.description],
    repairDate = this[Repair.repairDate].toString(),
    cost = this[Repair.cost],
    comment = this[Repair.comment]
)