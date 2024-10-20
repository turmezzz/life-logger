package com.example.plugins

import com.example.models.AddRequest
import com.example.models.DeleteRequest
import com.example.models.DoneRequest
import com.example.service.TicketService
import io.ktor.server.application.*
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val ticketService = TicketService()
    routing {
        post("add/{title}") {
            call.respond(ticketService.add(AddRequest(title = call.parameters["title"]!!)))
        }
        post("done/{id}") {
            call.respond(ticketService.done(DoneRequest(id = call.parameters["id"]!!.toLong())))
        }
        post("list") {
            call.respond(ticketService.list())
        }
        post("delete/{id}") {
            call.respond(ticketService.delete(DeleteRequest(id = call.parameters["id"]!!.toLong())))
        }
        post("table") {
            call.respondText(ticketService.table())
        }
    }
}
