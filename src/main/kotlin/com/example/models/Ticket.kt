package com.example.models


import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.`java-time`.datetime

@Serializable
data class Ticket(
    val id: Long,
    val title: String,
    val createdAt: LocalDateTime,
    val doneAt: LocalDateTime?,
)

object TicketTable : Table() {
    val id = long("id").autoIncrement()
    val title = varchar("title", 128)
    val createdAt = datetime("created_at").default(java.time.LocalDateTime.now())
    val doneAt = datetime("done_at").nullable()

    override val primaryKey = PrimaryKey(id)
}
