package com.adam_and_jan.routing

import com.adam_and_jan.dto.UserLoginDto
import com.adam_and_jan.models.User
import com.adam_and_jan.repository.UserRepository
import io.github.cdimascio.dotenv.dotenv
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.sql.*

fun Application.configureDatabases() {

    val dbconnection: Connection = connectToPostgres(embedded = true)
    val userRepository = UserRepository(dbconnection)

    routing {

        authenticate {
            get("/users") {
                try {
                    val users = userRepository.getAllUsers()
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
                    val user = userRepository.read(id)

                    if(user.email == extractPrincipalEmail(call))
                        call.respond(HttpStatusCode.OK, user)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.NotFound, e.message ?: "")
                }
            }

            get("/user") {
                try {
                    val email = extractPrincipalEmail(call) ?: throw IllegalArgumentException("Invalid Email")
                    val user = userRepository.findUserByEmail(email)
                    call.respond(HttpStatusCode.OK, user)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.NotFound, e.message ?: "")
                }
            }
        }

        post("/login") {
            val user = call.receive<UserLoginDto>()

            try {
                val result = userRepository.getLoginUser(user.email, user.password)
                call.respond(HttpStatusCode.OK, result)
            }
            catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, e.message ?: "")
            }
        }

        post("/users") {
            val user = call.receive<User>()

            try {
                val id = userRepository.create(user)
                call.respond(HttpStatusCode.Created, id)
            }
            catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, e.message ?: "")
            }
        }
    }
}

fun extractPrincipalEmail(call: ApplicationCall): String? =
    call.principal<JWTPrincipal>()
        ?.payload
        ?.getClaim("email")
        ?.asString()


fun Application.connectToPostgres(embedded: Boolean): Connection {
    Class.forName("org.postgresql.Driver")

    val dotenv = dotenv()

    if (embedded) {
        // DriverManager.getConnection("jdbc:<host>:<port>/<baza_danych>", "nazwa użytkownika", "hasło")
        return DriverManager.getConnection(dotenv["DATABASE_URL"], dotenv["DATABASE_USERNAME"], dotenv["DATABASE_PASSWORD"])
    } else {
        val url = environment.config.property("postgres.url").getString()
        val user = environment.config.property("postgres.user").getString()
        val password = environment.config.property("postgres.password").getString()

        return DriverManager.getConnection(url, user, password)
    }
}