package com.adam_and_jan.models

import kotlinx.serialization.Serializable
import org.mindrot.jbcrypt.BCrypt
import java.sql.Array

@Serializable
data class User (
    val id: Int,
    val username: String,
    val email: String,
    val password: String,
    val money: Int,
    val inventory: List<Int>,
){

    fun hashedPassword(): String {
        val salt = BCrypt.gensalt()
        return BCrypt.hashpw(password, salt)
    }
}