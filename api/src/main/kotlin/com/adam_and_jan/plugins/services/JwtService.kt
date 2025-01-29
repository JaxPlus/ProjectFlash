package com.adam_and_jan.plugins.services

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.Application
import io.ktor.server.auth.jwt.JWTCredential
import io.ktor.server.auth.jwt.JWTPrincipal
import java.util.Date


class JwtService(
    private val application: Application,
) {

    private val secret = "adergresdfhcgrdetfdhabtrhredf"//getConfigProperty("jwt.secret")
    private val issuer = "http://localhost"//getConfigProperty("jwt.issuer")
    private val audience = "my-audience"//getConfigProperty("jwt.audience")

    val realm = "my-realm"//getConfigProperty("jwt.realm")

    val jwtVerifier: JWTVerifier =
        JWT
            .require(Algorithm.HMAC256(secret))
            .withAudience(audience)
            .withIssuer(issuer)
            .build()

    fun createAccessToken(email: String): String =
        createJwtToken(email, 3_600_000)

    fun createRefreshToken(email: String): String =
        createJwtToken(email, 86_400_000)

    private fun createJwtToken(email: String, expireIn: Int): String =
        JWT
            .create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("email", email)
            .withExpiresAt(Date(System.currentTimeMillis() + expireIn))
            .sign(Algorithm.HMAC256(secret))

    fun customValidator(credential: JWTCredential): JWTPrincipal? {
        //val email = extractEmail(credential)
        //val foundUser = userRepository.findUserByEmail(email.toString())

        if (audienceMatches(credential)) {
            return JWTPrincipal(credential.payload)
        }

        return null
    }

    fun audienceMatches(audience: String): Boolean =
        this.audience == audience

    private fun JwtService.audienceMatches(credential: JWTCredential): Boolean =
        credential.payload.audience.contains(audience)

    private fun extractEmail(credential: JWTCredential): String? =
        credential.payload.getClaim("email").asString()

    private fun getConfigProperty(path: String) =
        application.environment.config.property(path).getString()
}