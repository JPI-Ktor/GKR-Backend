package com.jpi.server

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object DatabaseFactory {
    fun init() {
        val driverClassName = System.getenv("DRIVER_CLASS_NAME")
        val jdbcURL = System.getenv("JDBC_URL")
        val user = System.getenv("USER")
        val password = System.getenv("PASSWORD")

        val database = Database.connect(jdbcURL, driverClassName, user, password)
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}