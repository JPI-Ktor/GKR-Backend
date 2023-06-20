package com.jpi.domain.entity

import com.jpi.domain.State
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object Order: Table() {
    val userId = uuid("user_id").references(User.id)
//    val id
    val rentalReason = varchar("rental_reason", 100)
    val rentalDate = datetime("rental_date")
    val rentalState = enumerationByName("rental_state", 12, State::class)

    override val primaryKey = PrimaryKey(userId)
    // override val primaryKey = PrimaryKey(userId, id)
}