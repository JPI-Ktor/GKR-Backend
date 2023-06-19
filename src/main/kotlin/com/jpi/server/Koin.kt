package com.jpi.server

import com.jpi.di.repositoryModule
import com.jpi.di.useCaseModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin

fun Application.configureKoin() {
    install(Koin) {
        modules(repositoryModule, useCaseModule)
    }
}