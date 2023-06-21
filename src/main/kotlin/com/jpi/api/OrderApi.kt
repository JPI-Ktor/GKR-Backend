package com.jpi.api

import com.jpi.api.util.getAccessToken
import com.jpi.domain.model.request.ExtensionRequest
import com.jpi.domain.model.request.OrderRequest
import com.jpi.domain.usecase.auth.IsTokenValidUseCase
import com.jpi.domain.usecase.order.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.orderRoute() {
    val getRentalRequestListUseCase: GetRentalRequestListUseCase by inject()
    val getReturnRequestListUseCase: GetReturnRequestListUseCase by inject()
    val postExtensionRequestUseCase: PostExtensionRequestUseCase by inject()
    val postRentalRequestUseCase: PostRentalRequestUseCase by inject()
    val postReturnRequestUseCase: PostReturnRequestUseCase by inject()
    val isTokenValidUseCase: IsTokenValidUseCase by inject()

    route("/order") {
        get("/rental") {
            getAccessToken { isTokenValidUseCase(it) } ?: return@get
            val rentalRequestList = getRentalRequestListUseCase()
            if (rentalRequestList.isEmpty()) return@get call.respondText("Not Found RentalRequestList", status = HttpStatusCode.NotFound)
            call.respond(HttpStatusCode.OK, rentalRequestList)
        }

        get("/return") {
            getAccessToken { isTokenValidUseCase(it) } ?: return@get
            val returnRequestList = getReturnRequestListUseCase()
            if (returnRequestList.isEmpty()) return@get call.respondText("Not Found ReturnRequestList", status = HttpStatusCode.NotFound)
            call.respond(HttpStatusCode.OK, returnRequestList)
        }

        post("/rental") {
            getAccessToken { isTokenValidUseCase(it) } ?: return@post
            val orderRequest = call.receive<OrderRequest>()
            val rentalRequest = postRentalRequestUseCase(orderRequest)
            call.respond(HttpStatusCode.OK, rentalRequest)
        }

        post("/return") {
            getAccessToken { isTokenValidUseCase(it) } ?: return@post
            val orderRequest = call.receive<OrderRequest>()
            val returnRequest = postReturnRequestUseCase(orderRequest)
            call.respond(HttpStatusCode.OK, returnRequest)
        }

        post("/extension") {
            getAccessToken { isTokenValidUseCase(it) } ?: return@post
            val orderRequest = call.receive<ExtensionRequest>()
            val extensionRequest = postExtensionRequestUseCase(orderRequest)
            call.respond(HttpStatusCode.OK, extensionRequest)
        }
    }
}