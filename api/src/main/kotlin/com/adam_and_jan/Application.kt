package com.adam_and_jan

import com.adam_and_jan.routing.configureDatabases
import com.adam_and_jan.routing.configureRouting
import com.adam_and_jan.plugins.*
import com.adam_and_jan.plugins.services.JwtService
import com.adam_and_jan.plugins.services.ShopService
import com.adam_and_jan.plugins.services.UserService
import com.adam_and_jan.repository.RefreshTokenRepository
import com.adam_and_jan.repository.ShopRepository
import com.adam_and_jan.repository.UserRepository
import com.typesafe.config.ConfigFactory
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.CORS
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest


fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val config = ConfigFactory.parseResources("application.conf").resolve()

    install(CORS) {
        allowHost("localhost:5173")
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
        allowHeader("Content-Security-Policy")
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Patch)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
    }

    val client = createSupabaseClient(
//        supabaseUrl = applicationEnvironment().config.property("ktor.supabase.url").getString(),
//        supabaseKey = applicationEnvironment().config.property("ktor.supabase.key").getString(),
        supabaseUrl = config.getString("ktor.supabase.url"),
        supabaseKey = config.getString("ktor.supabase.key"),
    ) {
        install(Postgrest)
    }

    val userRepository = UserRepository(client)
    val refreshTokenRepository = RefreshTokenRepository()
    val shopRepository = ShopRepository(client)

    val jwtService = JwtService(this)
    val userService = UserService(userRepository, jwtService, refreshTokenRepository)
    val shopService = ShopService(shopRepository, userRepository)


    configureSecurity(jwtService)
    configureRouting(userService)
    configureDatabases(shopService, client)
    configureSerialization()
}
