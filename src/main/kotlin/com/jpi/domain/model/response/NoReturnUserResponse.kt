package com.jpi.domain.model.response

import com.jpi.domain.model.util.UUIDSerializer
import com.jpi.domain.util.Role
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class NoReturnUserResponse(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val profileUrl: String?,
    val name: String,
    val grade: Int?,
    val classNum: Int?,
    val number: Int?
)
