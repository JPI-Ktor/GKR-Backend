package com.jpi.di

import com.jpi.data.repository.*
import com.jpi.domain.repository.*
import org.koin.dsl.module

val repositoryModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<RepairRepository> { RepairRepositoryImpl() }
    single<OrderRepository> { OrderRepositoryImpl() }
    single<EquipmentRepository> { EquipmentRepositoryImpl() }
    single<ViolationRepository> { ViolationRepositoryImpl() }
}