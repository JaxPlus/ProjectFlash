package com.adam_and_jan

import com.adam_and_jan.plugins.*
import com.adam_and_jan.plugins.services.JwtService
import com.adam_and_jan.plugins.services.UserService
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
//import io.ktor.network.sockets.Connection
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.CORS
import java.sql.Connection

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(CORS) {
        allowHost("localhost:5173")
        allowHeader(HttpHeaders.ContentType)
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Patch)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
    }

    val dbconnection: Connection = connectToPostgres(embedded = true)
    val userService = UserService(dbconnection)
    val jwtService = JwtService(this, userService)

    configureRouting(jwtService)
    configureDatabases()
    configureSerialization()
    configureSecurity(jwtService)
}
