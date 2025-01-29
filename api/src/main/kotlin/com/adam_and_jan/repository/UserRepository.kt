package com.adam_and_jan.repository

import com.adam_and_jan.dto.UserCreateDto
import com.adam_and_jan.dto.UserDto
import com.adam_and_jan.mappers.UserMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.adam_and_jan.models.User
import com.adam_and_jan.models.UserInsert
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import org.mindrot.jbcrypt.BCrypt
import java.util.Base64
import kotlin.io.path.Path
import kotlin.io.path.createDirectory
import kotlin.io.path.writeBytes

class UserRepository(
    private  val client: SupabaseClient
) {

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

    suspend fun getUserByEmail(email: String): User = withContext(Dispatchers.IO) {
        val query = client.postgrest["users"]
            .select() {
                filter {
                    eq("email", email)
                }
            }

        return@withContext query.decodeSingle<User>()
    }

    suspend fun getAllUsers(): List<User> = withContext(Dispatchers.IO) {
        val query = client.postgrest["users"]
            .select()



        return@withContext query.decodeList<User>()
    }

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

    suspend fun setUserProfile(img: String, email: String): Boolean = withContext(Dispatchers.IO) {
        val user = getUserByEmail(email)

        val validImgBase64 = img.split(',')[1]

        val pictureBytes = Base64.getMimeDecoder().decode(validImgBase64)
        val path = Path("../files/users/${user.username}/avatar.png")
        path.writeBytes(pictureBytes)

        return@withContext true
    }

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
}