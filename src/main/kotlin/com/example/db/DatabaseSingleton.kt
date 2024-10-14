package com.example.db

import com.example.models.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.ApplicationConfig
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*
import org.jetbrains.exposed.sql.transactions.experimental.*

object DatabaseSingleton {
    fun init(datasourceConfig: ApplicationConfig) {
        val hikariConfig = HikariConfig().apply {
            jdbcUrl = datasourceConfig.property("url").getString()
            driverClassName = datasourceConfig.property("driver").getString()
            username = datasourceConfig.property("username").getString()
            password = datasourceConfig.property("password").getString()
            maximumPoolSize = datasourceConfig.property("maximumPoolSize").getString().toInt()
        }
        val dataSource = HikariDataSource(hikariConfig)
        val database = Database.connect(dataSource)

        transaction(database) {
            SchemaUtils.create(TicketTable)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}

fun configureDatabase(datasourceConfig: ApplicationConfig) {
    DatabaseSingleton.init(datasourceConfig)
}