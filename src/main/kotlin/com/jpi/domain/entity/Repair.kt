package com.jpi.domain.entity

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

object Repair: Table() {
    val productNumber = varchar("product_number", 20)
    val reason = varchar("reason", 100)
    val description = varchar("description", 200)
    val repairDate = date("date")
    val cost = integer("cost")
    val comment = text("comment")
}