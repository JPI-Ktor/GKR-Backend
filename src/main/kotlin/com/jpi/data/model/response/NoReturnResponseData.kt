package com.jpi.data.model.response

import com.jpi.domain.entity.User
import com.jpi.domain.model.response.NoReturnUserResponse
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.asNoReturnUserResponse() = NoReturnUserResponse (
    id = this[User.id],
    name = this[User.name],
    grade = this[User.grade],
    classNum = this[User.classNum],
    number = this[User.number]
)
