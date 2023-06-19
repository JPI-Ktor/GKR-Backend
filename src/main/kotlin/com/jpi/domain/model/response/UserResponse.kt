package com.jpi.domain.model.response

import com.jpi.domain.Role
import com.jpi.domain.model.util.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UserResponse(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val email: String,
    val name: String,
    val grade: Int?,
    val classNum: Int?,
    val number: Int?,
    val profileUrl: String?,
    val role: Role,
    val isRentalRestricted: Boolean
)
