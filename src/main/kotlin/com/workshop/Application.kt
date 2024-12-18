package com.workshop

import com.workshop.player.PlayerRepository
import com.workshop.room.RoomRepository
import com.workshop.tasks.FakeTaskRepository
import com.workshop.tasks.PostgresTaskRepository
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val repository = PostgresTaskRepository()
    //val taskRepository = FakeTaskRepository()
    configureDatabases()
    configureSerialization(repository)
    configureRouting(RoomRepository(), PlayerRepository())
}
