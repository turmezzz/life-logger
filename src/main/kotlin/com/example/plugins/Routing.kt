package com.example.plugins

import com.example.service.TicketService
import io.ktor.server.application.*
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val ticketService = TicketService()
    routing {
        post("add/{title}") {
            val title = call.parameters["title"]!!
            val id = ticketService.add(title)
            call.respondText("id: $id")
        }
        post("done/{id}") {
            val id = call.parameters["id"]!!.toLong()
            val doneAt = ticketService.done(id)
            call.respondText("doneAt: $doneAt")
        }
        post("list") {
            val tickets = ticketService.list()
            call.respond(tickets)
        }
        post("delete/{id}") {
            val id = call.parameters["id"]!!.toLong()
            ticketService.delete(id)
        }
    }
}
