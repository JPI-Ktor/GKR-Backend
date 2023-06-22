package com.jpi.domain.entity

import com.jpi.domain.util.Role
import org.jetbrains.exposed.sql.Table

object User: Table() {
    val id = uuid("id")
    val email = varchar("email",  20)
    val name = varchar("name", 20)
    val grade = integer("grade").nullable()
    val classNum = integer("class_num").nullable()
    val number = integer("number").nullable()
    val profileUrl = text("profile_url").nullable()
    val role = enumerationByName("role", 14, Role::class)

    override val primaryKey = PrimaryKey(id)
}