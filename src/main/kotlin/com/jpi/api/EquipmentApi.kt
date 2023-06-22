package com.jpi.api

import com.jpi.api.util.getAccessToken
import com.jpi.domain.model.request.EquipmentRequest
import com.jpi.domain.model.request.ModifyEquipmentRequest
import com.jpi.domain.usecase.auth.IsTokenValidUseCase
import com.jpi.domain.usecase.equipment.*
import com.jpi.domain.usecase.user.IsAdminUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.equipmentRoute() {
    val getAllEquipmentUseCase: GetAllEquipmentUseCase by inject()
    val getNotRentEquipmentUseCase: GetNotRentEquipmentUseCase by inject()
    val getIsRentEquipmentUseCase: GetIsRentEquipmentUseCase by inject()
    val equipmentUseCase: EquipmentInfoUseCase by inject()
    val addEquipmentUseCase: AddEquipmentUseCase by inject()
    val modifyEquipmentUseCase: ModifyEquipmentUseCase by inject()
    val deleteEquipmentUseCase: DeleteEquipmentUseCase by inject()

    val isTokenValidUseCase: IsTokenValidUseCase by inject()
    val isAdminUseCase: IsAdminUseCase by inject()

    route("/equipment") {
        get("/all") {
            getAccessToken { isTokenValidUseCase(it) } ?: return@get
            val getAll = getAllEquipmentUseCase()

            if (getAll.isEmpty())
                return@get call.respondText(status = HttpStatusCode.NotFound, text = "기자재가 존재하지 않습니다.")

            call.respond(status = HttpStatusCode.OK, message = getAll)
        }

        post {
            val equipment = call.receiveNullable<EquipmentRequest>()
                ?: return@post call.respondText(text = "잘못된 요청입니다.", status = HttpStatusCode.BadRequest)
            val accessToken = getAccessToken { isTokenValidUseCase(it) } ?: return@post
            if (!isAdminUseCase(accessToken))
                return@post call.respondText(text = "권한이 없습니다.", status = HttpStatusCode.Forbidden)

            val addEquipment = addEquipmentUseCase(equipmentRequest = equipment)

            call.respond(status = HttpStatusCode.Created, message = addEquipment)
        }

        get("/notrent") {
            getAccessToken { isTokenValidUseCase(it) } ?: return@get
            val getNotRent = getNotRentEquipmentUseCase()

            if (getNotRent.isEmpty())
                return@get call.respondText(status = HttpStatusCode.NotFound, text = "기자재가 존재하지 않습니다.")

            call.respond(status = HttpStatusCode.OK, message = getNotRent)
        }

        get("/isrent") {
            getAccessToken { isTokenValidUseCase(it) } ?: return@get
            val getIsRent = getIsRentEquipmentUseCase()

            if (getIsRent.isEmpty())
                return@get call.respondText(status = HttpStatusCode.NotFound, text = "기자재가 존재하지 않습니다.")

            call.respond(status = HttpStatusCode.OK, message = getIsRent)
        }

        get("/{productNumber}") {
            val productNumber = call.parameters["productNumber"].toString()
            getAccessToken { isTokenValidUseCase(it) } ?: return@get
            val equipmentInfo = equipmentUseCase(productNumber = productNumber)
                    ?: return@get call.respondText(text = "없는 기자재 입니다.", status = HttpStatusCode.NotFound)

            call.respond(status = HttpStatusCode.OK, message = equipmentInfo)
        }

        patch("/{productNumber}") {
            val productNumber = call.parameters["productNumber"].toString()
            val accessToken = getAccessToken { isTokenValidUseCase(it) } ?: return@patch
            if (!isAdminUseCase(accessToken))
                return@patch call.respondText(text = "권한이 없습니다.", status = HttpStatusCode.Forbidden)

            val equipment = call.receiveNullable<ModifyEquipmentRequest>()
                ?: return@patch call.respondText(text = "잘못된 요청입니다.", status = HttpStatusCode.BadRequest)

            val modifyEquipment = modifyEquipmentUseCase(productNumber = productNumber, equipmentRequest = equipment)
            if (!modifyEquipment)
                return@patch call.respondText(status = HttpStatusCode.NotFound, text = "없는 기자재 입니다.")

            call.respondText(status = HttpStatusCode.NoContent, text = "")
        }

        delete("/{productNumber}") {
            val productNum = call.parameters["productNumber"].toString()
            val accessToken = getAccessToken { isTokenValidUseCase(it) } ?: return@delete
            if (!isAdminUseCase(accessToken))
                return@delete call.respondText(text = "권한이 없습니다.", status = HttpStatusCode.Forbidden)

            val deleteEquipment = deleteEquipmentUseCase(productNumber = productNum)
            if (!deleteEquipment) {
                return@delete call.respondText(status = HttpStatusCode.NotFound, text = "없는 기자재 입니다.")
            }

            call.respondText(status = HttpStatusCode.NoContent, text = "삭제 완료")
        }
    }
}