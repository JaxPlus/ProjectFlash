package com.adam_and_jan.plugins

import com.adam_and_jan.plugins.services.JwtService
import io.ktor.server.application.Application
import io.ktor.server.auth.authentication
import io.ktor.server.auth.jwt.jwt

fun Application.configureSecurity(
    jwtService: JwtService
) {
    authentication {
        jwt {
            realm = jwtService.realm
            verifier(jwtService.jwtVerifier)
            validate { credential ->
                jwtService.customValidator(credential)
            }
        }

        jwt("another-auth") {
            realm = jwtService.realm
            verifier(jwtService.jwtVerifier)
            validate { credential ->
                jwtService.customValidator(credential)
            }
        }
    }
}