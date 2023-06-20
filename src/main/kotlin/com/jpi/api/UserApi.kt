package com.jpi.api

import com.jpi.data.model.request.UserRequest
import com.jpi.domain.usecase.auth.GetEmailByTokenUseCase
import com.jpi.domain.usecase.user.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.util.UUID

fun Route.userRoute() {
    val getStudentUseCase: GetStudentUseCase by inject()
    val getAllStudentUseCase: GetAllStudentUseCase by inject()
    val getEmailByTokenUseCase: GetEmailByTokenUseCase by inject()
    val restrictRentalUseCase: RestrictRentalUseCase by inject()
    val getUUIDUseCase: GetUUIDUseCase by inject()
    val logoutUseCase: LogoutUseCase by inject()

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
        val email = getEmailByTokenUseCase(accessToken = accessToken.removePrefix(tokenPrefix))
        val student = getStudentUseCase(email = email)
            ?: call.respondText(status = HttpStatusCode.NotFound, text = "유저를 찾을 수 없습니다.")

        call.respond(HttpStatusCode.OK, student)
    }
    get("user/all") {
        val allStudents = getAllStudentUseCase()

        call.respond(status = HttpStatusCode.OK, message = allStudents)
    }
    patch("user/restrict") {
        val userRequest = call.receiveNullable<UserRequest>() ?: return@patch call.respondText(
            status = HttpStatusCode.BadRequest,
            text = "잘못된 요청입니다."
        )
        restrictRentalUseCase(id = UUID.fromString(userRequest.id))
        call.respondText(status = HttpStatusCode.OK, text = "학생을 제재하였습니다.")
    }
    delete("user/logout") {
        val accessToken = call.request.headers[HttpHeaders.Authorization] ?: return@delete call.respondText(
            status = HttpStatusCode.BadRequest,
            text = "잘못된 요청입니다."
        )
        if (!accessToken.startsWith(tokenPrefix)) call.respondText(
            status = HttpStatusCode.Unauthorized,
            text = "유효하지 않은 토큰입니다."
        )
        val uuid = getUUIDUseCase(accessToken) ?: return@delete call.respondText(
            status = HttpStatusCode.NotFound,
            text = "유저를 찾을 수 없습니다."
        )
        logoutUseCase(uuid)
        call.respondText(status = HttpStatusCode.OK, text = "로그아웃 하였습니다.")
    }
}
