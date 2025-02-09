package com.adam_and_jan.routing

import com.adam_and_jan.dto.UserCreateDto
import com.adam_and_jan.dto.UserLoginDto
import com.adam_and_jan.repository.GameRepository
import com.adam_and_jan.plugins.services.ShopService
import com.adam_and_jan.repository.ShopRepository
import com.adam_and_jan.repository.UserRepository
import com.adam_and_jan.routing.request.ProfileImgRequest
import com.adam_and_jan.routing.request.ShopItemRequest
import com.adam_and_jan.routing.request.UsernameRequest
import io.github.jan.supabase.SupabaseClient
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureDatabases(
    shopService: ShopService,
    client: SupabaseClient
) {
    val userRepository = UserRepository(client)
    val gameRepository = GameRepository(client)
    val shopRepository = ShopRepository(client)

    routing {
        get("/games") {
            try {
                val games = gameRepository.getAllGames()
                call.respond(HttpStatusCode.OK, games)
            }
            catch (e: Exception) {
                println("Error: ${e.message}")
                call.respond(HttpStatusCode.NotFound)
            }
        }

        get("/games/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            try {
                val game = gameRepository.getGame(id)

                call.respond(HttpStatusCode.OK, game)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound, e.message ?: "")
            }
        }

        get("/games/files/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            try {
                val gameFile = gameRepository.getGameFile(id)

                call.respond(HttpStatusCode.OK, gameFile)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound, e.message ?: "")
            }
        }

        get("/game/thumbnail/{title}") {
            val title = call.parameters["title"] ?: throw IllegalArgumentException("Invalid Title")

            try {
                val gameImgUrl = gameRepository.getGameThumbnail(title)
                call.respond(HttpStatusCode.OK, gameImgUrl)
            }
            catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound, e.message ?: "")
            }
        }

        get("/tags") {
            try {
                val tags = gameRepository.getAllTags()
                call.respond(HttpStatusCode.OK, tags)
            }
            catch (e: Exception) {
                println("Error: ${e.message}")
                call.respond(HttpStatusCode.NotFound)
            }
        }

        get("/games/tag/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
            try {
                val games = gameRepository.getAllGamesByTag(id)

                call.respond(HttpStatusCode.OK, games)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound, e.message ?: "")
            }
        }

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
                    val user = userRepository.getUserById(id)

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

            patch("/user/username") {
                try {
                    val email = extractPrincipalEmail(call) ?: throw IllegalArgumentException("Invalid Email")
                    val usernameRequest = call.receive<UsernameRequest>()
                    val res = userRepository.setUsername(usernameRequest.editUsername, email)
                    println("RESPONSE: $res")

                    call.respond(HttpStatusCode.OK, res)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.NotFound, e.message ?: "")
                }
            }

            post("/shop") {
                try {
                    val email = extractPrincipalEmail(call) ?: throw IllegalArgumentException("Invalid Email")
                    val itemRequest = call.receive<ShopItemRequest>()

                    val res = shopService.buyShopItem(itemRequest.itemId, email)

                    call.respond(HttpStatusCode.OK, res)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "")
                }
            }

            post("/user/profile") {
                try {
                    val email = extractPrincipalEmail(call) ?: throw IllegalArgumentException("Invalid Email")
                    val img = call.receive<ProfileImgRequest>()

                    val res = userRepository.setUserProfile(img.img, email)

                    call.respond(HttpStatusCode.OK, res)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "")
                }
            }

            get("/user/profile") {
                try {
                    val email = extractPrincipalEmail(call) ?: throw IllegalArgumentException("Invalid Email")
                    val res = userRepository.getUserProfile(email)

                    call.respond(HttpStatusCode.OK, res)
                }
                catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "")
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
            val user = call.receive<UserCreateDto>()

            try {
                val id = userRepository.create(user)
                call.respond(HttpStatusCode.Created, id)
            }
            catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "${e.stackTrace}: ${e.message}")
            }
        }

        get("/shop") {
            try {
                val items = shopRepository.getAllShopItems()
                call.respond(HttpStatusCode.OK, items)
            }
            catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, e.message ?: "Unable to get shop items")
            }
        }

        get("/shop/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")

            try {
                val item = shopRepository.getShopItem(id)
                call.respond(HttpStatusCode.OK, item)
            }
            catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, e.message ?: "Unable to get shop items")
            }
        }
    }
}

fun extractPrincipalEmail(call: ApplicationCall): String? =
    call.principal<JWTPrincipal>()
        ?.payload
        ?.getClaim("email")
        ?.asString()