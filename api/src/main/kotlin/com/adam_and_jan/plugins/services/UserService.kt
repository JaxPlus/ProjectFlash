﻿package com.adam_and_jan.plugins.services

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import java.sql.Connection
import java.sql.Statement

@Serializable
data class User(val username: String, val email: String, val password: String)

class UserService(private val connection: Connection) {
    companion object {
        private const val SELECT_USER_BY_ID = """SELECT username, email FROM users WHERE id = ?"""
        private const val SELECT_USER_BY_USERNAME = """SELECT username FROM users WHERE username = ?"""
        private const val SELECT_USER_BY_EMAIL = """SELECT email FROM users WHERE email = ?"""
        private const val SELECT_ALL_USERS = """SELECT username, email, password FROM users"""
        private const val CREATE_USER = """INSERT INTO users (username, email, password, money, ranking_points) VALUES (?, ?, ?, ?, ?)"""
    }

    suspend fun create(user: User): Int = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS)
        statement.setString(1, user.username)
        statement.setString(2, user.email)
        // todo że tutaj mi chodzi o te encode czy coś
        statement.setString(3, user.password)
        statement.setInt(4, 0)
        statement.setInt(5, 0)

        val usernameStatement = connection.prepareStatement(SELECT_USER_BY_USERNAME)
        usernameStatement.setString(1, user.username)
        val isUsernameAvailable = usernameStatement.executeQuery()

        if (isUsernameAvailable.next()) {
            throw Exception("Username is already taken")
        }

        val emailStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL)
        emailStatement.setString(1, user.email)
        val isEmailAvailable = emailStatement.executeQuery()

        if (isEmailAvailable.next()) {
            throw Exception("Email already exists")
        }

        statement.executeUpdate()

        val generatedKeys = statement.generatedKeys

        if (generatedKeys.next()) {
            return@withContext generatedKeys.getInt(1)
        }
        else {
            throw Exception("Unable to retrieve the id of the newly created user.")
        }

    }

    suspend fun getAllUsers(): List<User> = withContext(Dispatchers.IO) {
        /**
         * trzeba zmienić to potem żeby wszystkich nie dawało a tylko jakąś część - paginacja czy jakoś tak
        */
        val statement = connection.prepareStatement(SELECT_ALL_USERS)
        val resultSet = statement.executeQuery()
        val users = mutableListOf<User>()

        while (resultSet.next()) {
            val username = resultSet.getString("username")
            val email = resultSet.getString("email")
            val password = resultSet.getString("password")
            users.add(User(username, email, password))
        }

        return@withContext users
    }

    suspend fun read(id: Int): User = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(SELECT_USER_BY_ID)
        statement.setInt(1, id)
        val resultSet = statement.executeQuery()

        if (resultSet.next()) {
            val username = resultSet.getString("username")
            val email = resultSet.getString("email")
            val password = resultSet.getString("password")
            return@withContext User(username, email, password)
        } else {
            throw Exception("Record not found")
        }
    }
}