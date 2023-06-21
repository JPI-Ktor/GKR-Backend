package com.jpi.domain.entity

import com.jpi.domain.util.RentStatus
import org.jetbrains.exposed.sql.Table

object Equipment: Table() {
    val productNumber = varchar("productNumber", 20)
    val name = varchar("name", 25)
    val image = text("image")
    val description = varchar("description", 200)
    val numberLeft = integer("numberLeft")
    val rentStatus = enumerationByName("rentStatus", 10, RentStatus::class)

    override val primaryKey = PrimaryKey(productNumber)
}