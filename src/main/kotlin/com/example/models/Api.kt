package com.example.models

import jnr.ffi.Struct.LONG
import kotlinx.serialization.Serializable

@Serializable
data class AddRequest(
    val title: String
)

@Serializable
data class AddResponse(
    val id: Long
)

@Serializable
data class DoneRequest(
    val id: Long
)

@Serializable
data class DoneResponse(
    val doneAt: kotlinx.datetime.LocalDateTime
)

@Serializable
data class ListResponse(
    val tickets: List<Ticket>
)

@Serializable
data class DeleteRequest(
    val id: Long
)



