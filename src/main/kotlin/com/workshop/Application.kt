package com.workshop

import com.workshop.player.PlayerRepository
import com.workshop.room.RoomRepository
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureDatabases()
    configureRouting(RoomRepository(), PlayerRepository())
}
