package com.adam_and_jan.plugins.services

import com.adam_and_jan.dto.UserLoginDto
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.Application
import io.ktor.server.auth.jwt.JWTCredential
import io.ktor.server.auth.jwt.JWTPrincipal
import java.util.Date


class JwtService(
    private val application: Application,
    private val userService: UserService,
) {

    private val secret = getConfigProperty("jwt.secret")
    private val issuer = getConfigProperty("jwt.issuer")
    private val audience = getConfigProperty("jwt.audience")

    val realm = getConfigProperty("jwt.realm")

    val jwtVerifier: JWTVerifier =
        JWT
            .require(Algorithm.HMAC256(secret))
            .withAudience(audience)
            .withIssuer(issuer)
            .build()

    suspend fun createJwtToken(userLoginDto: UserLoginDto): String? {
        val foundUser = userService.getLoginUser(userLoginDto.email, userLoginDto.password)

        return if (foundUser != false) {
            JWT
                .create()
                .withAudience(audience)
                .withIssuer(issuer)
                .withClaim("email", userLoginDto.email)
                .withExpiresAt(Date(System.currentTimeMillis() + 3_600_000))
                .sign(Algorithm.HMAC256(secret))
        } else null
    }

    suspend fun customValidator(credential: JWTCredential): JWTPrincipal? {
        val email = extractEmail(credential)
        val foundUser = userService.findUserByEmail(email.toString())

        if (foundUser != null && audienceMatches(credential)) {
            return JWTPrincipal(credential.payload)
        }

        return null;
    }

    private fun JwtService.audienceMatches(credential: JWTCredential): Boolean =
        credential.payload.audience.contains(audience)

    private fun extractEmail(credential: JWTCredential): String? =
        credential.payload.getClaim("email").asString()

    private fun getConfigProperty(path: String) =
        application.environment.config.property(path).getString()
}