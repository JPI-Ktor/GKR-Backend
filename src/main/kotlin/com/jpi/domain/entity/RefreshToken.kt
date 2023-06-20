package com.jpi.domain.entity

import org.jetbrains.exposed.sql.Table

object RefreshToken: Table() {
    val id = uuid("id").references(User.id)
    val refreshToken = text("refresh_token")

    override val primaryKey = PrimaryKey(id)
}