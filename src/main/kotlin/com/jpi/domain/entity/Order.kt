package com.jpi.domain.entity

import com.jpi.domain.util.State
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object Order: Table() {
    val userId = uuid("user_id").references(User.id)
    val equipmentId = varchar("product_number", 20).references(Equipment.productNumber)
    val rentalReason = varchar("rental_reason", 100)
    val rentalDate = datetime("rental_date")
    val rentalState = enumerationByName("rental_state", 15, State::class)
    val returnDate = datetime("return_date")

    override val primaryKey = PrimaryKey(userId, equipmentId)
}