package com.jpi.api

import com.jpi.api.util.getAccessToken
import com.jpi.domain.model.request.RepairRequest
import com.jpi.domain.usecase.auth.IsTokenValidUseCase
import com.jpi.domain.usecase.repair.AddRepairHistoryUseCase
import com.jpi.domain.usecase.repair.DeleteRepairHistoryUseCase
import com.jpi.domain.usecase.repair.GetRepairHistoryUseCase
import com.jpi.domain.usecase.repair.ModifyRepairHistoryUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.repairRoute() {
    val addRepairHistoryUseCase: AddRepairHistoryUseCase by inject()
    val modifyRepairHistoryUseCase: ModifyRepairHistoryUseCase by inject()
    val deleteRepairHistoryUseCase: DeleteRepairHistoryUseCase by inject()
    val isTokenValidUseCase: IsTokenValidUseCase by inject()
    val getRepairHistoryUseCase: GetRepairHistoryUseCase by inject()

    route("repair") {
        post {
            getAccessToken { isTokenValidUseCase(it) } ?: return@post

            val repairRequest = call.receiveNullable<RepairRequest>() ?: return@post call.respondText(
                status = HttpStatusCode.BadRequest,
                text = "잘못된 요청입니다."
            )
            addRepairHistoryUseCase(repairRequest = repairRequest)
            call.respondText(status = HttpStatusCode.Created, text = "수리 내역을 정상적으로 추가하였습니다.")
        }
        get {
            getAccessToken { isTokenValidUseCase(it) } ?: return@get

            val productNumber = call.request.queryParameters["productNumber"] ?: return@get call.respondText(
                status = HttpStatusCode.BadRequest,
                text = "잘못된 요청입니다."
            )
            val repairHistory = getRepairHistoryUseCase(productNumber = productNumber)

            call.respond(status = HttpStatusCode.OK, repairHistory)
        }
        patch {
            getAccessToken { isTokenValidUseCase(it) } ?: return@patch

            val repairRequest = call.receiveNullable<RepairRequest>() ?: return@patch call.respondText(
                status = HttpStatusCode.BadRequest,
                text = "잘못된 요청입니다."
            )
            modifyRepairHistoryUseCase(repairRequest = repairRequest)
            call.respondText(status = HttpStatusCode.OK, text = "수리 내역을 수정하였습니다.")
        }
        delete {
            getAccessToken { isTokenValidUseCase(it) } ?: return@delete

            val productNumber = call.request.queryParameters["productNumber"] ?: return@delete call.respondText(
                status = HttpStatusCode.BadRequest,
                text = "잘못된 요청입니다."
            )
            deleteRepairHistoryUseCase(productNumber = productNumber)
            call.respondText(status = HttpStatusCode.OK, text = "수리 내역을 성공적으로 삭제하였습니다.")
        }
    }
}