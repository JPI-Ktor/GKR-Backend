package com.jpi.domain.entity

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

object Violation: Table() {
    val userId = uuid("user_id").references(User.id)
    val reason = varchar("reason", 100)
    val date = date("date")
}