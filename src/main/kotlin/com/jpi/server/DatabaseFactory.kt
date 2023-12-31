package com.jpi.server

import com.jpi.domain.entity.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        val driverClassName = System.getenv("DRIVER_CLASS_NAME")
        val jdbcURL = System.getenv("JDBC_URL")
        val user = System.getenv("USER")
        val password = System.getenv("PASSWORD")

        val database = Database.connect(jdbcURL, driverClassName, user, password)

        transaction(database) {
            SchemaUtils.create(User, RefreshToken, Order, Equipment, Repair, Violation)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}