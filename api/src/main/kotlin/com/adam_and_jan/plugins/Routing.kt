package com.adam_and_jan.plugins

import io.ktor.http.ContentType
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText(
                contentType = ContentType.parse("text/html; charset=utf-8"),
                text = "haj"
            )
        }
    }
}
