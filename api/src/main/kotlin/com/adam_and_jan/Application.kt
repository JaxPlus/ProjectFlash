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
        supabaseUrl = "https://lftqqullqnydsseyxjep.supabase.co",//environment.config.property("ktor.supabase.url").getString(),
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImxmdHFxdWxscW55ZHNzZXl4amVwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzczMDU0NDAsImV4cCI6MjA1Mjg4MTQ0MH0.Zjw4S0rCpw4GUQHhpHZIgBgN2JVeUX6hg6YmOYELrOE"//environment.config.property("ktor.supabase.key").getString()
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
