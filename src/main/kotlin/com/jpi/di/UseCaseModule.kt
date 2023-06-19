package com.jpi.di

import com.jpi.domain.usecase.user.GetAllStudentUseCase
import com.jpi.domain.usecase.user.GetStudentUseCase
import org.koin.dsl.module

val useCaseModule = module {
    // User
    single { GetStudentUseCase(get()) }
    single { GetAllStudentUseCase(get()) }
}