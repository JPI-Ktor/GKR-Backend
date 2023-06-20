package com.jpi.api

import com.jpi.domain.model.request.EquipmentRequest
import com.jpi.domain.usecase.equipment.*
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
    val deleteEquipmentUseCase: DeleteEquipmentUseCase by inject()

    get("/equipment/all") {
        val getAll = getAllEquipmentUseCase()

        if (getAll.isEmpty()) {
            return@get call.respondText(status = HttpStatusCode.NotFound, text = "기자재가 존재하지 않습니다.")
        }
        call.respond(status = HttpStatusCode.OK, message = getAll)
    }

    post("/equipment") {
        val equipment = call.receiveNullable<EquipmentRequest>()
            ?: return@post call.respondText(text = "잘못된 요청입니다.", status = HttpStatusCode.BadRequest)

        val addEquipment = addEquipmentUseCase(equipment)

        call.respond(status = HttpStatusCode.Created, message = addEquipment)
    }

    get("/equipment/notrent") {
        val getNotRent = getNotRentEquipmentUseCase()

        call.respond(status = HttpStatusCode.OK, message = getNotRent)
    }

    get("/equipment/isrent") {
        val getIsRent = getIsRentEquipmentUseCase()

        call.respond(status = HttpStatusCode.OK, message = getIsRent)
    }

    get("/equipment/{productNumber}") {
        val productNumber = call.parameters["productNumber"].toString()
        val equipmentInfo = equipmentUseCase(productNumber)
            ?: return@get call.respondText(text = "없는 기자재 입니다.", status = HttpStatusCode.NotFound)

        call.respond(status = HttpStatusCode.OK, message = equipmentInfo)
    }

    delete("/equipment/{productNumber}") {
        val productNum = call.parameters["productNumber"].toString()
        val deleteEquipment = deleteEquipmentUseCase(productNum)
        if (!deleteEquipment) {
            return@delete call.respondText(status = HttpStatusCode.NotFound, text = "없는 기자재 입니다.")
        }

        call.respondText(status = HttpStatusCode.NoContent, text = "삭제 완료")
    }
}