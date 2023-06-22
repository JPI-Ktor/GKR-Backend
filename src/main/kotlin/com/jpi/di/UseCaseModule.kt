package com.jpi.di

import com.jpi.domain.usecase.auth.GetEmailByTokenUseCase
import com.jpi.domain.usecase.auth.IsTokenValidUseCase
import com.jpi.domain.usecase.auth.ReissueTokenUseCase
import com.jpi.domain.usecase.auth.SignInUseCase
import com.jpi.domain.usecase.order.*
import com.jpi.domain.usecase.equipment.*
import com.jpi.domain.usecase.repair.AddRepairHistoryUseCase
import com.jpi.domain.usecase.repair.DeleteRepairHistoryUseCase
import com.jpi.domain.usecase.repair.GetRepairHistoryUseCase
import com.jpi.domain.usecase.repair.ModifyRepairHistoryUseCase
import com.jpi.domain.usecase.user.*
import org.koin.dsl.module

val useCaseModule = module {
    // User
    single { GetStudentUseCase(get()) }
    single { GetAllStudentUseCase(get()) }
    single { RestrictRentalUseCase(get()) }
    single { GetUUIDUseCase(get()) }
    single { LogoutUseCase(get()) }
    single { IsAdminUseCase(get()) }

    // Auth
    single { ReissueTokenUseCase(get()) }
    single { SignInUseCase(get()) }
    single { GetEmailByTokenUseCase(get()) }
    single { IsTokenValidUseCase(get()) }

    // Repair
    single { AddRepairHistoryUseCase(get()) }
    single { ModifyRepairHistoryUseCase(get()) }
    single { DeleteRepairHistoryUseCase(get()) }
    single { GetRepairHistoryUseCase(get()) }

    // equipment
    single { GetAllEquipmentUseCase(get()) }
    single { GetNotRentEquipmentUseCase(get()) }
    single { GetIsRentEquipmentUseCase(get()) }
    single { EquipmentInfoUseCase(get()) }
    single { AddEquipmentUseCase(get()) }
    single { ModifyEquipmentUseCase(get()) }
    single { DeleteEquipmentUseCase(get()) }

    // Order
    single { GetRentalRequestListUseCase(get()) }
    single { GetWaitRequestListUseCase(get()) }
    single { PostRentalRequestUseCase(get()) }
    single { PostReturnRequestUseCase(get()) }
    single { PostExtensionRequestUseCase(get()) }
    single { GetRentalEquipmentUseCase(get()) }
    single { DecideAcceptOrRejectUseCase(get()) }
}