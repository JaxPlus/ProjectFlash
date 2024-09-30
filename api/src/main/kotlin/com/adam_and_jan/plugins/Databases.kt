package com.adam_and_jan.plugins

import com.adam_and_jan.plugins.services.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.sql.*

fun Application.configureDatabases() {
    val dbconnection: Connection = connectToPostgres(embedded = true)
    val userService = UserService(dbconnection)

    routing {
        get("/users/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            try {
                val user = userService.read(id)
                call.respondText(contentType = ContentType.parse("application/json"), text = user.toString())
            } catch (e: Exception) {
                println(e)
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}

fun Application.connectToPostgres(embedded: Boolean): Connection {
    Class.forName("org.postgresql.Driver")
    if (embedded) {
        // DriverManager.getConnection("jdbc:<host>:<port>/<baza_danych>", "nazwa użytkownika", "hasło")
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/projectFlash", "postgres", "1234")
    } else {
        val url = environment.config.property("postgres.url").getString()
        val user = environment.config.property("postgres.user").getString()
        val password = environment.config.property("postgres.password").getString()

        return DriverManager.getConnection(url, user, password)
    }
}
