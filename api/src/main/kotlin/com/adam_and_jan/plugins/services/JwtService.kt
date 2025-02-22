package com.adam_and_jan.plugins.services

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.typesafe.config.ConfigFactory
import io.ktor.server.application.*
import io.ktor.server.auth.jwt.*
import java.util.*


class JwtService(
    private val application: Application,
) {
    val config = ConfigFactory.parseResources("application.conf").resolve()
//    private val secret = applicationEnvironment().config.property("jwt.secret").getString()
//    private val issuer = applicationEnvironment().config.property("jwt.issuer").getString()
//    private val audience = applicationEnvironment().config.property("jwt.audience").getString()

    private val secret = config.getString("jwt.secret")
    private val issuer = config.getString("jwt.issuer")
    private val audience = config.getString("jwt.audience")

//    val realm = applicationEnvironment().config.property("jwt.realm").getString()
    val realm = config.getString("jwt.realm")

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