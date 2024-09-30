package com.adam_and_jan.plugins.services

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import java.sql.Connection

@Serializable
data class User(val username: String, val email: String/*, val password: String, val money: Int, val rankingPoints: Int */) {
    override fun toString(): String {
        return "$username $email"
    }
}

class UserService(private val connection: Connection) {
    suspend fun read(id: Int): User = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement("SELECT users.username, users.email FROM users WHERE id = ?")
        statement.setInt(1, id)
        val resultSet = statement.executeQuery()

        if (resultSet.next()) {
            val username = resultSet.getString("username")
            val email = resultSet.getString("email")
            return@withContext User(username, email)
        } else {
            throw Exception("Record not found")
        }
    }
}