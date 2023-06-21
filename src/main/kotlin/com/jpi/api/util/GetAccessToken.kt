package com.jpi.api.util

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*

const val tokenPrefix = "Bearer "

suspend fun PipelineContext<*, ApplicationCall>.getAccessToken(
    isTokenValidUseCase: suspend (String) -> Boolean
): String? {
    val accessToken = call.request.headers[HttpHeaders.Authorization]

    if (accessToken == null) {
        call.respondText(status = HttpStatusCode.BadRequest, text = "토큰 값이 올바르지 않습니다.")
        return null
    }

    if (!accessToken.startsWith(tokenPrefix) || !isTokenValidUseCase(accessToken)) {
        call.respondText(status = HttpStatusCode.Unauthorized, text = "유효하지 않은 토큰입니다.")
        return null
    }

    return accessToken
}