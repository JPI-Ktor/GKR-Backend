package com.jpi.data.model.response

import com.jpi.domain.entity.Violation
import com.jpi.domain.model.response.ViolationResponse
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.asViolationResponse() = ViolationResponse(
    userId = this[Violation.userId],
    reason = this[Violation.reason],
    date = this[Violation.date].toString()
)