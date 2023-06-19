package com.jpi.api

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

    get("user") {
        val email = ""
        val student = getStudentUseCase(email = email)
            ?: call.respondText(status = HttpStatusCode.NotFound, text = "유저를 찾을 수 없습니다.")

        call.respond(HttpStatusCode.OK, student)
    }
    get("user/all") {
        val allStudents = getAllStudentUseCase()

        call.respond(status = HttpStatusCode.OK, message = allStudents)
    }
}
