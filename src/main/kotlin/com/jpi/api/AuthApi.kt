package com.jpi.api

import com.jpi.domain.model.request.SignInRequest
import com.jpi.domain.usecase.auth.ReissueTokenUseCase
import com.jpi.domain.usecase.auth.SignInUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.authRoute() {
    val signInUseCase: SignInUseCase by inject()
    val reissueTokenUseCase: ReissueTokenUseCase by inject()

    route("auth") {
        post("/signin") {
            val signInRequest = call.receiveNullable<SignInRequest>() ?: return@post call.respondText(
                status = HttpStatusCode.BadRequest,
                text = "잘못된 요청입니다."
            )

            val token = signInUseCase(signInRequest = signInRequest)

            call.respond(status = HttpStatusCode.OK, message = token)
        }

        patch("/reissue") {
            val refreshToken = call.request.headers["Refresh-Token"] ?: return@patch call.respondText(
                status = HttpStatusCode.BadRequest,
                text = "잘못된 요청입니다."
            )

            val token = reissueTokenUseCase(refreshToken = refreshToken)

            call.respond(status = HttpStatusCode.OK, message = token)
        }
    }
}