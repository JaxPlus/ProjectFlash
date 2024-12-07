package com.adam_and_jan.plugins

import com.adam_and_jan.dto.UserLoginDto
import com.adam_and_jan.plugins.services.JwtService
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    jwtService: JwtService
) {
    routing {
        route("/api/auth") {
            post {
                val loginRequest = call.receive<UserLoginDto>()

                val token = jwtService.createJwtToken(loginRequest)

                token?.let {
                    call.respond(hashMapOf("token" to it))
                } ?: call.respond(HttpStatusCode.Unauthorized)
            }
        }

        get("/") {
            call.respondText(
                contentType = ContentType.parse("text/html; charset=utf-8"),
                text = "haj"
            )
        }
    }
}
