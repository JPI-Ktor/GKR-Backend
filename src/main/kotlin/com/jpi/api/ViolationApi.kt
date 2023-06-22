package com.jpi.api

import com.jpi.api.util.getAccessToken
import com.jpi.domain.model.request.ViolationRequest
import com.jpi.domain.usecase.auth.IsTokenValidUseCase
import com.jpi.domain.usecase.user.GetUUIDUseCase
import com.jpi.domain.usecase.user.IsAdminUseCase
import com.jpi.domain.usecase.violation.GetViolationUseCase
import com.jpi.domain.usecase.violation.RentalViolationUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.violationRoute() {
    val getViolationUseCase: GetViolationUseCase by inject()
    val rentalViolationUseCase: RentalViolationUseCase by inject()
    val isTokenValidUseCase: IsTokenValidUseCase by inject()
    val isAdminUseCase: IsAdminUseCase by inject()
    val getUUIDUseCase: GetUUIDUseCase by inject()

    route("violation") {
        get {
            val accessToken = getAccessToken { isTokenValidUseCase(it) } ?: return@get
            val userId = getUUIDUseCase(accessToken = accessToken) ?: return@get call.respondText(
                status = HttpStatusCode.NotFound,
                text = "유저를 찾을 수 없습니다."
            )
            val violation = getViolationUseCase(userId = userId)
            call.respond(status = HttpStatusCode.OK, message = violation)
        }
        post {
            val accessToken = getAccessToken { isTokenValidUseCase(it) } ?: return@post
            if (!isAdminUseCase(accessToken = accessToken)) return@post call.respondText(
                status = HttpStatusCode.Forbidden,
                text = "권한이 없습니다."
            )
            val violationRequest = call.receive<ViolationRequest>()
            rentalViolationUseCase(violationRequest = violationRequest)
            call.respondText(status = HttpStatusCode.OK, text = "학생을 제재하였습니다.")
        }
    }
}