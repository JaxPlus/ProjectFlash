package com.adam_and_jan.repository

import com.adam_and_jan.dto.UserCreateDto
import com.adam_and_jan.dto.UserDto
import com.adam_and_jan.mappers.UserMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.Statement
import com.adam_and_jan.models.User
import com.adam_and_jan.models.UserInsert
import com.adam_and_jan.routing.request.ProfileImgRequest
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns

import org.mindrot.jbcrypt.BCrypt
import java.sql.ResultSet
import java.util.Base64
import kotlin.io.path.Path
import kotlin.io.path.createDirectory
import kotlin.io.path.writeBytes

class UserRepository(
    private  val client: SupabaseClient
) {
    companion object {
        private const val SELECT_USER_BY_ID = """SELECT username, email, password, money, inventory FROM users WHERE id = ?"""
        private const val CHECK_IF_USERNAME_EXISTS = """SELECT username FROM users WHERE username = ?"""
        private const val CHECK_IF_EMAIL_EXISTS = """SELECT email FROM users WHERE email = ?"""
        private const val SELECT_ALL_USERS = """SELECT * FROM users"""
        private const val CREATE_USER = """INSERT INTO users (username, email, password, money, ranking_points, inventory) VALUES (?, ?, ?, ?, ?, ?)"""
        private const val SELECT_LOGIN_USER = """SELECT email, password FROM users WHERE email = ?"""
        private const val IF_USER_EXISTS = """SELECT 1 FROM users WHERE email = ?"""
        private const val SET_USER_USERNAME = """UPDATE users SET username = ? WHERE email = ?"""
        private const val SET_USER_MONEY = """UPDATE users SET money = ? WHERE id = ?"""
        private const val SET_USER_INVENTORY = """UPDATE users SET inventory = ? WHERE id = ?"""

        private const val SELECT_USER_BY_EMAIL = """SELECT * FROM users WHERE email = ?"""
    }

//    suspend fun create(userDto: UserCreateDto): Int = withContext(Dispatchers.IO) {
//        val user = User(-1, userDto.username, userDto.email, userDto.password, 0, listOf())
//
//        val statement = connection.prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS)
//        statement.setString(1, user.username)
//        statement.setString(2, user.email)
//        statement.setString(3, user.hashedPassword())
//        statement.setInt(4, 100000)
//        statement.setInt(5, 0)
//        statement.setArray(6, connection.createArrayOf("INTEGER", listOf(1, 2).toTypedArray()))
//
//        val usernameStatement = connection.prepareStatement(CHECK_IF_USERNAME_EXISTS)
//        usernameStatement.setString(1, user.username)
//        val isUsernameAvailable = usernameStatement.executeQuery()
//
//        if (isUsernameAvailable.next()) {
//            throw Exception("Username is already taken")
//        }
//
//        val emailStatement = connection.prepareStatement(CHECK_IF_EMAIL_EXISTS)
//        emailStatement.setString(1, user.email)
//        val isEmailAvailable = emailStatement.executeQuery()
//
//        if (isEmailAvailable.next()) {
//            throw Exception("Email already exists")
//        }
//
//        statement.executeUpdate()
//
//        val generatedKeys = statement.generatedKeys
//
//        if (generatedKeys.next()) {
//            Path("../files/users/${user.username}").createDirectory()
//            return@withContext generatedKeys.getInt(1)
//        } else {
//            throw Exception("Unable to retrieve the id of the newly created user.")
//        }
//
//    }

    suspend fun create(userDto: UserCreateDto): Int = withContext(Dispatchers.IO) {

        val salt = BCrypt.gensalt()
        val hashedPassword = BCrypt.hashpw(userDto.password, salt)

        val user = UserInsert(userDto.username, userDto.email, hashedPassword, 10000, 0, listOf(1, 2))

        val usernameExists = client.postgrest["users"]
            .select() {
                filter {
                    eq("username", user.username)
                }
            }
        if(!usernameExists.decodeList<User>().isEmpty()) {
            throw Exception("Username is already taken")
        }


        val emailExists = client.postgrest["users"]
            .select() {
                filter {
                    eq("email", user.email)
                }
            }
        if(!emailExists.decodeList<User>().isEmpty()) {
            throw Exception("Email already exists")
        }

        val userInsert = client.postgrest["users"]
            .insert(user) {
                select()
            }.decodeSingle<User>()


        if(!userInsert.id.toString().isEmpty()) {
            Path("../files/users/${user.username}").createDirectory()
            return@withContext userInsert.id
        } else {
            throw Exception("Unable to retrieve the id of the newly created user.")
        }
    }

//    suspend fun findUserByEmail(email: String): UserDto = withContext(Dispatchers.IO) {
//        val statement = connection.prepareStatement(SELECT_USER_BY_EMAIL)
//        statement.setString(1, email)
//        val resultSet = statement.executeQuery()
//
//        if (resultSet.next()) {
//            val user = getUser(resultSet)
//
//            return@withContext UserMapper.toDto(user)
//        } else {
//            throw Exception("User not found")
//        }
//    }

    suspend fun findUserByEmail(email: String): UserDto = withContext(Dispatchers.IO) {
        val query = client.postgrest["users"]
            .select() {
                filter {
                    eq("email", email)
                }
            }
        val user = query.decodeSingle<User>()
        return@withContext UserMapper.toDto(user)
    }

//    suspend fun getUserByEmail(email: String): User = withContext(Dispatchers.IO) {
//        val statement = connection.prepareStatement(SELECT_USER_BY_EMAIL)
//        statement.setString(1, email)
//        val resultSet = statement.executeQuery()
//
//        if (resultSet.next()) {
//            val user = getUser(resultSet)
//
//            return@withContext user
//        } else {
//            throw Exception("User not found")
//        }
//    }

    suspend fun getUserByEmail(email: String): User = withContext(Dispatchers.IO) {
        val query = client.postgrest["users"]
            .select() {
                filter {
                    eq("email", email)
                }
            }

        return@withContext query.decodeSingle<User>()
    }

//    suspend fun getAllUsers(): List<User> = withContext(Dispatchers.IO) {
//        /**
//         * trzeba zmienić to potem żeby wszystkich nie dawało a tylko jakąś część - paginacja czy jakoś tak
//         */
//        val statement = connection.prepareStatement(SELECT_ALL_USERS)
//        val resultSet = statement.executeQuery()
//        val users = mutableListOf<User>()
//
//        while (resultSet.next()) {
//            val user = getUser(resultSet)
//            users.add(user)
//        }
//
//        return@withContext users
//    }

    suspend fun getAllUsers(): List<User> = withContext(Dispatchers.IO) {
        val query = client.postgrest["users"]
            .select()



        return@withContext query.decodeList<User>()
    }

//    suspend fun getUserById(id: Int): UserDto = withContext(Dispatchers.IO) {
//        val statement = connection.prepareStatement(SELECT_USER_BY_ID)
//        statement.setInt(1, id)
//        val resultSet = statement.executeQuery()
//
//        if (resultSet.next()) {
//            val user = getUser(resultSet)
//
//            return@withContext UserMapper.toDto(user)
//        } else {
//            throw Exception("Record not found")
//        }
//    }

    suspend fun getUserById(id: Int): UserDto = withContext(Dispatchers.IO) {
        val query = client.postgrest["users"]
            .select() {
                filter {
                    eq("id", id)
                }
            }

        val user = query.decodeSingle<User>()
        return@withContext UserMapper.toDto(user)
    }

//    suspend fun getLoginUser(email: String, password: String): Boolean = withContext(Dispatchers.IO) {
//        checkUser(email)
//
//        val statement = connection.prepareStatement(SELECT_LOGIN_USER)
//        statement.setString(1, email)
//        val resultSet = statement.executeQuery()
//        resultSet.next()
//
//        val passFromDB = resultSet.getString("password")
//        val checkPw = BCrypt.checkpw(password, passFromDB)
//
//        if (checkPw) {
//            return@withContext true
//        } else {
//            throw Exception("Email or password do not match.")
//        }
//    }

    suspend fun getLoginUser(email: String, password: String): Boolean = withContext(Dispatchers.IO) {
        val query = client.postgrest["users"]
            .select() {
                filter {
                    eq("email", email)
                }
            }

        val result = query.decodeSingle<User>()
        val passFromDB = result.password
        val checkPw = BCrypt.checkpw(password, passFromDB)

        return@withContext checkPw
    }

//    suspend fun setUsername(username: String, email: String): Boolean = withContext(Dispatchers.IO) {
//        checkUser(email)
//
//        val statement = connection.prepareStatement(SET_USER_USERNAME)
//        statement.setString(1, username)
//        statement.setString(2, email)
//
//        val res = statement.executeUpdate()
//
//        if (res == 1) {
//            return@withContext true
//        }
//        else {
//            throw Exception("Unable to set the username for $username")
//        }
//    }

    suspend fun setUsername(username: String, email: String): Boolean = withContext(Dispatchers.IO) {
        checkUser(email)

        client.postgrest["users"]
            .update({
                    set("username", username)
                }
            ) {
                filter {
                    eq("email", email)
                }
            }

        return@withContext true
    }

//    suspend fun setUserMoney(id: Int, money: Int): Boolean = withContext(Dispatchers.IO) {
//        val statement = connection.prepareStatement(SET_USER_MONEY)
//        statement.setInt(1, money)
//        statement.setInt(2, id)
//
//        val res = statement.executeUpdate()
//
//        if (res == 1) {
//            return@withContext true
//        }
//        else {
//            throw Exception("Unable to set the user money for $id")
//        }
//    }

    suspend fun setUserMoney(id: Int, money: Int): Boolean = withContext(Dispatchers.IO) {
        client.postgrest["users"]
            .update({
                set("money", money)
            }
            ) {
                filter {
                    eq("id", id)
                }
            }

        return@withContext true
    }

//    suspend fun addToUserInventory(id: Int, itemId: Int, inv: List<Int>): Boolean = withContext(Dispatchers.IO) {
//        val statement = connection.prepareStatement(SET_USER_INVENTORY)
//        statement.setArray(1, connection.createArrayOf("INTEGER", (inv + itemId).toTypedArray()))
//        statement.setInt(2, id)
//
//        val res = statement.executeUpdate()
//
//        if (res == 1) {
//            return@withContext true
//        }
//        else {
//            throw Exception("Unable to set the user inventory for $id")
//        }
//    }

    suspend fun addToUserInventory(id: Int, itemId: Int, inv: List<Int>): Boolean = withContext(Dispatchers.IO) {



        client.postgrest["users"]
            .update({
                set("inventory", (inv + itemId).toTypedArray())
            }
            ) {
                filter {
                    eq("id", id)
                }
            }

        return@withContext true
    }

//    suspend fun setUserProfile(img: String, email: String): Boolean = withContext(Dispatchers.IO) {
//        val user = getUserByEmail(email)
//
//        val validImgBase64 = img.split(',')[1]
//
//        val pictureBytes = Base64.getMimeDecoder().decode(validImgBase64)
//        val path = Path("../files/users/${user.username}/avatar.png")
//        path.writeBytes(pictureBytes)
//
//        return@withContext true
//    }

    suspend fun setUserProfile(img: String, email: String): Boolean = withContext(Dispatchers.IO) {
        val user = getUserByEmail(email)

        val validImgBase64 = img.split(',')[1]

        val pictureBytes = Base64.getMimeDecoder().decode(validImgBase64)
        val path = Path("../files/users/${user.username}/avatar.png")
        path.writeBytes(pictureBytes)

        return@withContext true
    }

//    private fun checkUser(email: String) {
//        val userExists = connection.prepareStatement(IF_USER_EXISTS)
//        userExists.setString(1, email)
//        val userExistsResult = userExists.executeQuery()
//
//        if (!userExistsResult.next()) {
//            throw Exception("User doesn't exist. You must create your account!")
//        }
//    }

    private suspend fun checkUser(email: String) {
        val query = client.postgrest["users"]
            .select() {
                filter {
                    eq("email", email)
                }
                limit(count = 1)
            }

        val result = query.decodeSingle<User>()

        if (result.toString().isEmpty()) {
            throw Exception("User doesn't exist. You must create your account!")
        }
    }

//    private fun getUser(resultSet: ResultSet): User {
//        val id = resultSet.getInt("id")
//        val username = resultSet.getString("username")
//        val email = resultSet.getString("email")
//        val password = resultSet.getString("password")
//        val money = resultSet.getInt("money")
//        val inventory = resultSet.getString("inventory").trimStart('{').trimEnd('}').split(",")
//            .filter { it.isNotEmpty() }
//            .map { it.trim().toInt() }
//
//        return User(id, username, email, password, money, inventory)
//    }
}