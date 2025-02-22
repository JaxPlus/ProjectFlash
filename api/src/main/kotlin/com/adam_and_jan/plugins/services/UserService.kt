﻿package com.adam_and_jan.plugins.services

import com.adam_and_jan.dto.UserCreateDto
import com.adam_and_jan.dto.UserDto
import com.adam_and_jan.dto.UserLoginDto
import com.adam_and_jan.models.User
import com.adam_and_jan.repository.RefreshTokenRepository
import com.adam_and_jan.repository.UserRepository
import com.adam_and_jan.routing.response.AuthResponse
import com.auth0.jwt.interfaces.DecodedJWT

class UserService(
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    suspend fun create(user: UserCreateDto): Int =
        userRepository.create(user)

    suspend fun findUserByEmail(email: String): UserDto =
        userRepository.findUserByEmail(email)

    suspend fun getAllUsers(): List<User> =
        userRepository.getAllUsers()

    suspend fun getUserById(id: Int): UserDto =
        userRepository.getUserById(id)

    suspend fun getLoginUser(email: String, password: String): Boolean =
        userRepository.getLoginUser(email, password)

    suspend fun authenticate(dto: UserLoginDto): AuthResponse? {
        val email = dto.email

        try {
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
        catch (e: Exception) {
            return null
        }
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