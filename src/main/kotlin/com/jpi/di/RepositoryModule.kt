package com.jpi.di

import com.jpi.data.repository.AuthRepositoryImpl
import com.jpi.data.repository.OrderRepositoryImpl
import com.jpi.data.repository.UserRepositoryImpl
import com.jpi.domain.repository.AuthRepository
import com.jpi.domain.repository.OrderRepository
import com.jpi.domain.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<OrderRepository> { OrderRepositoryImpl() }
}