package com.adam_and_jan.models

import kotlinx.serialization.Serializable
import org.mindrot.jbcrypt.BCrypt

@Serializable
data class User (
    val username: String,
    val email: String,
    val password: String
){

    fun hashedPassword(): String {
        val salt = BCrypt.gensalt()
        return BCrypt.hashpw(password, salt)
    }
}