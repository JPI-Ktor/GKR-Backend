package com.jpi.server

import com.jpi.api.authRoute
import com.jpi.api.orderRoute
import com.jpi.api.userRoute
import io.ktor.server.routing.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {
        userRoute()
        authRoute()
        orderRoute()
    }
}
