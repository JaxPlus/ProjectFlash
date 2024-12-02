package com.adam_and_jan.plugins.services

import com.adam_and_jan.dto.UserDto
import com.adam_and_jan.mappers.UserMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.Statement
import com.adam_and_jan.models.User
import org.mindrot.jbcrypt.BCrypt

class UserService(private val connection: Connection) {
    companion object {
        private const val SELECT_USER_BY_ID = """SELECT username, email, password FROM users WHERE id = ?"""
        private const val SELECT_USER_BY_USERNAME = """SELECT username FROM users WHERE username = ?"""
        private const val SELECT_USER_BY_EMAIL = """SELECT email FROM users WHERE email = ?"""
        private const val SELECT_ALL_USERS = """SELECT username, email, password FROM users"""
        private const val CREATE_USER = """INSERT INTO users (username, email, password, money, ranking_points) VALUES (?, ?, ?, ?, ?)"""
        private const val SELECT_LOGIN_USER = """SELECT email, password FROM users WHERE email = ?"""
        private const val IF_USER_EXISTS = """SELECT 1 FROM users WHERE email = ?"""
    }

    suspend fun create(user: User): Int = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS)
        statement.setString(1, user.username)
        statement.setString(2, user.email)
        statement.setString(3, user.hashedPassword())
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

    suspend fun read(id: Int): UserDto = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(SELECT_USER_BY_ID)
        statement.setInt(1, id)
        val resultSet = statement.executeQuery()

        if (resultSet.next()) {
            val username = resultSet.getString("username")
            val email = resultSet.getString("email")
            val password = resultSet.getString("password")
            return@withContext UserMapper.toDto(User(username, email, password))
        } else {
            throw Exception("Record not found")
        }
    }

    suspend fun getLoginUser(email: String, password: String): Boolean = withContext(Dispatchers.IO) {
        val userExists = connection.prepareStatement(IF_USER_EXISTS)
        userExists.setString(1, email)
        val userExistsResult = userExists.executeQuery()

        if (!userExistsResult.next()) {
            throw Exception("User doesn't exist. You must create your account!")
        }

        val statement = connection.prepareStatement(SELECT_LOGIN_USER)
        statement.setString(1, email)
        val resultSet = statement.executeQuery()
        resultSet.next()

        val passFromDB = resultSet.getString("password")
        val checkPw = BCrypt.checkpw(password, passFromDB)

        if(checkPw) {
            return@withContext true
        } else {
            throw Exception("Email or password do not match.")
        }
    }
}