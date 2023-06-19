package com.jpi.domain.model.response

import com.jpi.domain.Role
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GAuthUserResponse(
    val email: String,
    val name: String,
    val grade: Int?,
    val classNum: Int?,
    @SerialName("num") val number: Int?,
    val profileUrl: String?,
    val role: Role,
)