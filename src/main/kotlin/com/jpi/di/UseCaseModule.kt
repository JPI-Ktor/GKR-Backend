package com.jpi.di

import com.jpi.domain.usecase.auth.GetEmailByTokenUseCase
import com.jpi.domain.usecase.auth.IsTokenValidUseCase
import com.jpi.domain.usecase.auth.ReissueTokenUseCase
import com.jpi.domain.usecase.auth.SignInUseCase
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
}