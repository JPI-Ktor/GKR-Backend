package com.jpi.api.util

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*

const val tokenPrefix = "Bearer "

suspend fun PipelineContext<*, ApplicationCall>.getAccessToken(
    isTokenValidUseCase: suspend (String) -> Boolean
): String? {
    val token = call.request.headers[HttpHeaders.Authorization]

    if (token == null) {
        call.respondText(status = HttpStatusCode.BadRequest, text = "잘못된 요청입니다.")
        return null
    }

    if (!token.startsWith(tokenPrefix) || !isTokenValidUseCase(token)) {
        call.respondText(status = HttpStatusCode.Unauthorized, text = "유효하지 않은 토큰입니다.")
        return null
    }

    return token
}