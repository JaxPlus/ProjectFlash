package com.adam_and_jan.routing

import com.adam_and_jan.dto.NekosImg
import com.adam_and_jan.dto.UserLoginDto
import com.adam_and_jan.plugins.services.UserService
import com.adam_and_jan.routing.request.RefreshTokenRequest
import com.adam_and_jan.routing.response.AuthResponse
import com.adam_and_jan.routing.response.RefreshTokenResponse
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    userService: UserService
) {
    routing {
        route("/api/auth") {
            post {
                val loginRequest = call.receive<UserLoginDto>()

                val authResponse: AuthResponse? = userService.authenticate(loginRequest)

                authResponse?.let {
                    call.respond(authResponse)
                } ?: call.respond(HttpStatusCode.Unauthorized)
            }

            post("/refresh") {
                val request = call.receive<RefreshTokenRequest>()

                val newAccessToken: String? = userService.refreshToken(request.token)

                newAccessToken?.let {
                    call.respond(
                        RefreshTokenResponse(it)
                    )
                } ?: call.respond(
                    message = HttpStatusCode.Unauthorized
                )
            }
        }

        get("/api") {
            call.respondText(
                contentType = ContentType.parse("text/html; charset=utf-8"),
                text = "haj"
            )
        }

        // temp
        get("/api/random-profile") {
            val client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    json()
                }
            }
            val response: List<NekosImg> = client.get("https://api.nekosapi.com/v4/images/random?limit=5&rating=safe").body()
            call.respond(HttpStatusCode.OK, response)
            client.close()
        }
    }
}
