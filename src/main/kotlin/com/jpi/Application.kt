package com.jpi

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.jpi.server.DatabaseFactory
import com.jpi.server.configureKoin
import com.jpi.server.configureRouting
import com.jpi.server.configureSerialization

fun main() {
    embeddedServer(Netty, port = 443, host = "https://port-0-gkr-backend-dihik2mljfecyah.sel4.cloudtype.app", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    DatabaseFactory.init()
    configureSerialization()
    configureRouting()
    configureKoin()
}
