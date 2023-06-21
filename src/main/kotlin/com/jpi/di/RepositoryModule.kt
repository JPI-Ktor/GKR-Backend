package com.jpi.di

import com.jpi.data.repository.AuthRepositoryImpl
import com.jpi.data.repository.OrderRepositoryImpl
import com.jpi.data.repository.RepairRepositoryImpl
import com.jpi.data.repository.UserRepositoryImpl
import com.jpi.data.repository.EquipmentRepositoryImpl
import com.jpi.domain.repository.AuthRepository
import com.jpi.domain.repository.OrderRepository
import com.jpi.domain.repository.RepairRepository
import com.jpi.domain.repository.UserRepository
import com.jpi.domain.repository.EquipmentRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<RepairRepository> { RepairRepositoryImpl() }
    single<OrderRepository> { OrderRepositoryImpl() }
    single<EquipmentRepository> { EquipmentRepositoryImpl() }
}