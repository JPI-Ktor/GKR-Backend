package com.jpi.domain.usecase.user

import com.jpi.domain.repository.UserRepository
import java.util.UUID

class LogoutUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(id: UUID) = repository.logout(id)
}