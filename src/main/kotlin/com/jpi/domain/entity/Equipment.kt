package com.jpi.domain.entity

import com.jpi.domain.util.RentStatus
import org.jetbrains.exposed.sql.Table

object Equipment: Table() {
    val productNumber = varchar("product_number", 20)
    val name = varchar("name", 25)
    val image = text("image")
    val description = varchar("description", 200)
    val numberLeft = integer("number_left")
    val rentStatus = enumerationByName("rent_status", 10, RentStatus::class)

    override val primaryKey = PrimaryKey(productNumber)
}