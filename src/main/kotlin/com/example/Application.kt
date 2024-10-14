package com.example

import com.example.db.configureDatabase
import com.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

//fun main() {
////    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
//    embeddedServer(Netty, port = (System.getenv("PORT") ?: "5000").toInt(), module = Application::module)
//        .start(wait = true)
//}

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    configureDatabase(environment.config.config("ktor.datasource"))
    configureSecurity()
    configureSerialization()
    configureRouting()
}
