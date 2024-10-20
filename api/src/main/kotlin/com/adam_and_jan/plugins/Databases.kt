package com.adam_and_jan.plugins

import com.adam_and_jan.plugins.services.User
import com.adam_and_jan.plugins.services.UserService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.sql.*

fun Application.configureDatabases() {
    val dbconnection: Connection = connectToPostgres(embedded = true)
    val userService = UserService(dbconnection)

    routing {
        get("/users") {
            try {
                val users = userService.getAllUsers()
                call.respond(HttpStatusCode.OK, users)
            }
            catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        get("/users/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            try {
                val user = userService.read(id)
                call.respond(HttpStatusCode.OK, user)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        post("/users") {
            val user = call.receive<User>()

            try {
                val id = userService.create(user)
                call.respond(HttpStatusCode.Created, id)
            }
            catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, e.message ?: "")
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
