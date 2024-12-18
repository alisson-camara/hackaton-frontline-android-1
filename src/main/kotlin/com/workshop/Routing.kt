package com.workshop

import com.workshop.player.IPlayerRepository
import com.workshop.player.Player
import com.workshop.room.IRoomRepository
import com.workshop.room.Room
import com.workshop.room.RoomPlayer
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.http.content.staticResources
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.configureRouting(
    roomRepository: IRoomRepository,
    playerRepository: IPlayerRepository
) {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
    routing {

        get("/") {
            call.respondText("Hello World!")
        }

        post("/create-room") {
            val room = call.parameters["room"]
            val moderator = call.parameters["moderator"]
            if (room == null || moderator == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val roomModel = Room(
                name = room,
                moderator = moderator
            )

            val roomId = roomRepository.createRoom(
                roomModel
            )

            val newPlayer = Player(
                name = moderator,
                roomId = roomId
            )
            playerRepository.createPlayer(player = newPlayer)

            val localPlayers = roomRepository.getPlayersByRoomId(roomId)
            val roomPlayer = RoomPlayer(
                room = roomModel,
                players = localPlayers
            )

            val selectedRoom = roomRepository.getRoom(roomId)
            if (selectedRoom != null) {
                call.respond(roomPlayer)
            } else {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
        }

        // Static plugin. Try to access `/static/index.html`
        staticResources("/static", "static")
    }
}
