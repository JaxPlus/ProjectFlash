package com.adam_and_jan.plugins.services

import io.ktor.server.
import io.ktor.server.application.Application

class JwtService(
    private val application: Application,
    private val userService: UserService
) {

    private val secret = getConfigProperty("jwt.secret")
    private val issuer = getConfigProperty("jwt.issuer")
    private val audience = getConfigProperty("jwt.audience")

    val realm = getConfigProperty("jwt.realm")

    val jwtVerifier: JWTverifier

    private fun getConfigProperty(path: String) =
        application.environment.config.property(path).getString()
}