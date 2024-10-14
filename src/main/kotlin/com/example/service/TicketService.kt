package com.example.service

import com.example.Log
import com.example.db.TicketDao
import com.example.models.Ticket
import java.time.LocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime

class TicketService {
    private val ticketDao = TicketDao()

    suspend fun add(title: String): Long {
        val ticket = ticketDao.insertTicket(title)
        Log("ticket with title $title was added with id ${ticket.id}")
        return ticket.id
    }

    suspend fun done(id: Long): kotlinx.datetime.LocalDateTime {
        val ticket = ticketDao.setDoneAt(id, LocalDateTime.now().toKotlinLocalDateTime())
        Log("ticket with id: $id was done at ${ticket.doneAt}")
        return ticket.doneAt!!
    }

    suspend fun list(): List<Ticket> {
        return ticketDao.selectTickets()
    }

    suspend fun delete(id: Long) {
        ticketDao.delete(id)
        Log("deleted ticket with id: $id")
    }
}