﻿package com.adam_and_jan.plugins

import com.adam_and_jan.models.User
import com.adam_and_jan.plugins.services.UserService
import io.github.cdimascio.dotenv.dotenv
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
                println("Error: ${e.message}")
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

        post("/login") {
            val email = call.parameters["email"]?.toString() ?: throw IllegalArgumentException("Invalid email")
            val password = call.parameters["password"]?.toString() ?: throw IllegalArgumentException("Invalid password")

            try {
                val result = userService.getLoginUser(email, password)
                call.respond(HttpStatusCode.OK, result)
            }
            catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, e.message ?: "")
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

    val dotenv = dotenv()

    if (embedded) {
        // DriverManager.getConnection("jdbc:<host>:<port>/<baza_danych>", "nazwa użytkownika", "hasło")
        // te rzeczy można teraz edytować w pliku .env
        return DriverManager.getConnection(dotenv["DATABASE_URL"], dotenv["DATABASE_USERNAME"], dotenv["DATABASE_PASSWORD"])
    } else {
        val url = environment.config.property("postgres.url").getString()
        val user = environment.config.property("postgres.user").getString()
        val password = environment.config.property("postgres.password").getString()

        return DriverManager.getConnection(url, user, password)
    }
}
