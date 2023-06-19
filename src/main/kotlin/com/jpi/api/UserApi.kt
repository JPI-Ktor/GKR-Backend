package com.jpi.api

import com.jpi.domain.usecase.auth.GetEmailByTokenUseCase
import com.jpi.domain.usecase.user.GetAllStudentUseCase
import com.jpi.domain.usecase.user.GetStudentUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.userRoute() {
    val getStudentUseCase: GetStudentUseCase by inject()
    val getAllStudentUseCase: GetAllStudentUseCase by inject()
    val getEmailByTokenUseCase: GetEmailByTokenUseCase by inject()

    val tokenPrefix = "Bearer "

    get("user") {
        val accessToken = call.request.headers[HttpHeaders.Authorization] ?: return@get call.respondText(
            status = HttpStatusCode.BadRequest,
            text = "잘못된 요청입니다."
        )
        if (!accessToken.startsWith(tokenPrefix)) call.respondText(
            status = HttpStatusCode.Unauthorized,
            text = "유효하지 않은 토큰입니다."
        )
        val email = getEmailByTokenUseCase(accessToken = accessToken.removePrefix("Bearer "))
        val student = getStudentUseCase(email = email)
            ?: call.respondText(status = HttpStatusCode.NotFound, text = "유저를 찾을 수 없습니다.")

        call.respond(HttpStatusCode.OK, student)
    }
    get("user/all") {
        val allStudents = getAllStudentUseCase()

        call.respond(status = HttpStatusCode.OK, message = allStudents)
    }
}
