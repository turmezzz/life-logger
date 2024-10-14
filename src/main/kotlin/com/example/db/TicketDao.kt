package com.example.db

import com.example.db.DatabaseSingleton.dbQuery
import com.example.models.Ticket
import com.example.models.TicketTable
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import org.jetbrains.exposed.sql.ResultRow
import kotlinx.datetime.toKotlinInstant
import kotlinx.datetime.toKotlinLocalDateTime
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update


class TicketDao {
    private fun resultRowToTicket(row: ResultRow) = Ticket(
        id = row[TicketTable.id],
        title = row[TicketTable.title],
        createdAt = row[TicketTable.createdAt].toKotlinLocalDateTime(),
        doneAt = row[TicketTable.doneAt]?.toKotlinLocalDateTime(),
    )

    suspend fun insertTicket(title: String): Ticket = dbQuery {
        val insertStatement = TicketTable.insert {
            it[TicketTable.title] = title
        }
        insertStatement.resultedValues!!.single().let(::resultRowToTicket)
    }

    suspend fun setDoneAt(id: Long, doneAt: LocalDateTime): Ticket = dbQuery {
        TicketTable.update({ TicketTable.id eq id }) {
            it[TicketTable.doneAt] = doneAt.toJavaLocalDateTime()
        }
        val selectStament = TicketTable.select { TicketTable.id eq id }.limit(1).map(::resultRowToTicket)
        assert(selectStament.size == 1) { "Expected to find exactly 1" }
        selectStament[0]
    }

    suspend fun selectTickets(): List<Ticket> = dbQuery {
        TicketTable.selectAll().map(::resultRowToTicket)
    }

    suspend fun delete(id: Long) = dbQuery {
        TicketTable.deleteWhere { TicketTable.id eq id }
    }
}
