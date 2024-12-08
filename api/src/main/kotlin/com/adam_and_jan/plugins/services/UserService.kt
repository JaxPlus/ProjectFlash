package com.adam_and_jan.plugins.services

import com.adam_and_jan.dto.UserDto
import com.adam_and_jan.dto.UserLoginDto
import com.adam_and_jan.mappers.UserMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.Statement
import com.adam_and_jan.models.User
import com.adam_and_jan.repository.RefreshTokenRepository
import com.adam_and_jan.repository.UserRepository
import com.adam_and_jan.routing.response.AuthResponse
import com.auth0.jwt.interfaces.DecodedJWT
import org.mindrot.jbcrypt.BCrypt

class UserService(
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    suspend fun create(user: User): Int =
        userRepository.create(user)

    suspend fun findUserByEmail(email: String): UserDto =
        userRepository.findUserByEmail(email)

    suspend fun getAllUsers(): List<User> =
        userRepository.getAllUsers()

    suspend fun read(id: Int): UserDto =
        userRepository.read(id)

    suspend fun getLoginUser(email: String, password: String): Boolean =
        userRepository.getLoginUser(email, password)

    suspend fun authenticate(dto: UserLoginDto): AuthResponse? {
        val email = dto.email

        return if (getLoginUser(email, dto.password)) {
            val accessToken = jwtService.createAccessToken(email)
            val refreshToken = jwtService.createRefreshToken(email)

            refreshTokenRepository.save(refreshToken, email)

            AuthResponse(
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        } else null
    }

    suspend fun refreshToken(token: String): String? {
        val decodedRefreshToken = verifyRefreshToken(token)
        val persistedEmail = refreshTokenRepository.findEmailByToken(token)

        return if (decodedRefreshToken != null && persistedEmail != null) {
            val foundUser = findUserByEmail(persistedEmail)
            val emailFromRefreshToken = decodedRefreshToken.getClaim("email").asString()

            if (emailFromRefreshToken == foundUser.email)
                jwtService.createAccessToken(persistedEmail)
            else
                null

        } else null
    }

    private fun verifyRefreshToken(token: String): DecodedJWT? {
        val decodedJWT = decodedJWT(token)

        return decodedJWT?.let {
            val audienceMatches = jwtService.audienceMatches(it.audience.first())

            if (audienceMatches)
                decodedJWT
            else
                null
        }
    }

    private fun decodedJWT(token: String): DecodedJWT? = try {
        jwtService.jwtVerifier.verify(token)
    } catch (e: Exception) {
        null
    }
}