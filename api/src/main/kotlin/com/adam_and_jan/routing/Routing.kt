package com.adam_and_jan.routing

import com.adam_and_jan.dto.UserLoginDto
import com.adam_and_jan.plugins.services.UserService
import com.adam_and_jan.routing.request.RefreshTokenRequest
import com.adam_and_jan.routing.response.AuthResponse
import com.adam_and_jan.routing.response.RefreshTokenResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    userService: UserService
) {
//    routing {
//        route("/api/auth") {
//            post {
//                val loginRequest = call.receive<UserLoginDto>()
//
//                val authResponse: AuthResponse? = userService.authenticate(loginRequest)
//
//                authResponse?.let {
//                    call.respond(authResponse)
//                } ?: call.respond(HttpStatusCode.Unauthorized)
//            }
//
//            post("/refresh") {
//                val request = call.receive<RefreshTokenRequest>()
//
//                val newAccessToken: String? = userService.refreshToken(request.token)
//
//                newAccessToken?.let {
//                    call.respond(
//                        RefreshTokenResponse(it)
//                    )
//                } ?: call.respond(
//                    message = HttpStatusCode.Unauthorized
//                )
//            }
//        }
//
//        get("/api") {
//            call.respondText(
//                contentType = ContentType.parse("text/html; charset=utf-8"),
//                text = "haj"
//            )
//        }
//    }
}
