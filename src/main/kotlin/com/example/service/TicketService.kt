package com.example.service

import com.example.Log
import com.example.db.TicketDao
import com.example.models.AddRequest
import com.example.models.AddResponse
import com.example.models.DeleteRequest
import com.example.models.DoneRequest
import com.example.models.DoneResponse
import com.example.models.ListResponse
import java.time.LocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import de.m3y.kformat.table

class TicketService {
    private val ticketDao = TicketDao()

    suspend fun add(request: AddRequest): AddResponse {
        val ticket = ticketDao.insertTicket(request.title)
        Log("ticket with title ${request.title} was added with id ${ticket.id}")
        return AddResponse(id = ticket.id)
    }

    suspend fun done(request: DoneRequest): DoneResponse {
        val ticket = ticketDao.setDoneAt(request.id, LocalDateTime.now().toKotlinLocalDateTime())
        Log("ticket with id: ${request.id} was done at ${ticket.doneAt}")
        return DoneResponse(doneAt = ticket.doneAt!!)
    }

    suspend fun list(): ListResponse {
        return ListResponse(tickets = ticketDao.selectTickets())
    }

    suspend fun table(): String {
        val tickets = ticketDao.selectTickets()
        return table {
            header("id", "title", "created_At", "done_at")
            for (ticket in tickets) {
                row(ticket.id, ticket.title, ticket.createdAt, ticket.doneAt ?: "~")
            }
        }.render().trim().toString()
    }

    suspend fun delete(request: DeleteRequest) {
        ticketDao.delete(request.id)
        Log("deleted ticket with id: ${request.id}")
    }
}