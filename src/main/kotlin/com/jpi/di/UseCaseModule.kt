package com.jpi.di

import com.jpi.domain.usecase.auth.GetEmailByTokenUseCase
import com.jpi.domain.usecase.auth.ReissueTokenUseCase
import com.jpi.domain.usecase.auth.SignInUseCase
import com.jpi.domain.usecase.user.GetAllStudentUseCase
import com.jpi.domain.usecase.user.GetStudentUseCase
import org.koin.dsl.module

val useCaseModule = module {
    // User
    single { GetStudentUseCase(get()) }
    single { GetAllStudentUseCase(get()) }

    // Auth
    single { ReissueTokenUseCase(get()) }
    single { SignInUseCase(get()) }
    single { GetEmailByTokenUseCase(get()) }
}