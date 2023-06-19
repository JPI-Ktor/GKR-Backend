package com.jpi.domain.model.response

import com.jpi.domain.Role
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val email: String,
    val name: String,
    val grade: Int?,
    val classNum: Int?,
    val number: Int?,
    val profileUrl: String?,
    val role: Role,
    val isRentalRestricted: Boolean
)
