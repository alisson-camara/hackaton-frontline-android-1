package com.workshop

import com.workshop.player.PlayerRepository
import com.workshop.room.RoomRepository
import com.workshop.tasks.FakeTaskRepository
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    //val repository = PostgresTaskRepository()
    val taskRepository = FakeTaskRepository()
    configureSerialization(taskRepository)
    //configureDatabases()
    configureRouting(RoomRepository(), PlayerRepository())
}
