package com.jpi.di

import com.jpi.data.repository.UserRepositoryImpl
import com.jpi.domain.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<UserRepository> { UserRepositoryImpl() }
}