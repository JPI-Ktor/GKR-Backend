package com.jpi.api

import com.jpi.api.util.getAccessToken
import com.jpi.data.model.request.*
import com.jpi.domain.usecase.auth.IsTokenValidUseCase
import com.jpi.domain.usecase.order.*
import com.jpi.domain.usecase.user.GetUUIDUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.orderRoute() {
    val getRentalRequestListUseCase: GetRentalRequestListUseCase by inject()
    val getWaitRequestListUseCase: GetWaitRequestListUseCase by inject()
    val postExtensionRequestUseCase: PostExtensionRequestUseCase by inject()
    val postRentalRequestUseCase: PostRentalRequestUseCase by inject()
    val postReturnRequestUseCase: PostReturnRequestUseCase by inject()
    val isTokenValidUseCase: IsTokenValidUseCase by inject()
    val getRentalEquipmentUseCase: GetRentalEquipmentUseCase by inject()
    val getUUIDUseCase: GetUUIDUseCase by inject()
    val decideAcceptOrRejectUseCase: DecideAcceptOrRejectUseCase by inject()
    val getNoReturnUserListUseCase: GetNoReturnUserListUseCase by inject()

    route("/order") {
        get("/rental") {
            getAccessToken { isTokenValidUseCase(it) } ?: return@get
            val rentalRequestList = getRentalRequestListUseCase()
            if (rentalRequestList.isEmpty()) return@get call.respondText("Not Found RentalRequestList", status = HttpStatusCode.NotFound)
            call.respond(HttpStatusCode.OK, rentalRequestList)
        }

        get("/wait") {
            getAccessToken { isTokenValidUseCase(it) } ?: return@get
            val waitRequestList = getWaitRequestListUseCase()
            if (waitRequestList.isEmpty()) return@get call.respondText("Not Found ReturnRequestList", status = HttpStatusCode.NotFound)
            call.respond(HttpStatusCode.OK, waitRequestList)
        }

        post("/rental") {
            val accessToken = getAccessToken { isTokenValidUseCase(it) } ?: return@post
            val userId = getUUIDUseCase(accessToken) ?: return@post call.respondText("Not Found UUID", status = HttpStatusCode.NotFound)
            val orderRequestData = call.receive<OrderRequestData>()
            postRentalRequestUseCase(orderRequestData.asOrderRequest(userId))
            call.respondText("대여 요청이 완료되었습니다", status = HttpStatusCode.OK)
        }

        post("/return") {
            val accessToken = getAccessToken { isTokenValidUseCase(it) } ?: return@post
            val userId = getUUIDUseCase(accessToken) ?: return@post call.respondText("Not Found UUID", status = HttpStatusCode.NotFound)
            val orderRequestData = call.receive<OrderRequestData>()
            postReturnRequestUseCase(orderRequestData.asOrderRequest(userId))
            call.respondText("반납 요청이 완료되었습니다", status = HttpStatusCode.OK)
        }

        post("/extension") {
            val accessToken = getAccessToken { isTokenValidUseCase(it) } ?: return@post
            val userId = getUUIDUseCase(accessToken) ?: return@post call.respondText("Not Found UUID", status = HttpStatusCode.NotFound)
            val extensionRequestData = call.receive<ExtensionRequestData>()
            postExtensionRequestUseCase(extensionRequestData.asExtensionRequest(userId))
            call.respondText("연장 요청이 완료되었습니다", status = HttpStatusCode.OK)
        }

        get {
            val accessToken = getAccessToken { isTokenValidUseCase(it) } ?: return@get
            val userId = getUUIDUseCase(accessToken) ?: return@get call.respondText("Not Found UUID", status = HttpStatusCode.NotFound)
            val equipmentList = getRentalEquipmentUseCase(userId)
            call.respond(HttpStatusCode.OK, equipmentList)
        }

        post {
            val accessToken = getAccessToken { isTokenValidUseCase(it) } ?: return@post
            val userId = getUUIDUseCase(accessToken) ?: return@post call.respondText("Not Found UUID", status = HttpStatusCode.NotFound)
            val decideRequestData = call.receive<DecideRequestData>()
            decideAcceptOrRejectUseCase(decideRequestData.asDecideRequest(userId))
            call.respondText("요청 결과가 나왔습니다", status = HttpStatusCode.OK)
        }

        get("/noreturn") {
            getAccessToken { isTokenValidUseCase(it) } ?: return@get
            val noReturnUserList = getNoReturnUserListUseCase()
            call.respond(HttpStatusCode.OK, noReturnUserList)
        }
    }
}