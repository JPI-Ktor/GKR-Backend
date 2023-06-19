package com.jpi.data.model.response

import com.jpi.domain.entity.User
import com.jpi.domain.model.response.UserResponse
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.asUserResponse() = UserResponse(
    email = this[User.email],
    name = this[User.name],
    grade = this[User.grade],
    classNum = this[User.classNum],
    number = this[User.number],
    profileUrl = this[User.profileUrl],
    role = this[User.role],
    isRentalRestricted = this[User.isRentalRestricted]
)
