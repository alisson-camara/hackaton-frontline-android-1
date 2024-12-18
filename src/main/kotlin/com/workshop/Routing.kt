package com.workshop

import com.workshop.player.IPlayerRepository
import com.workshop.player.Player
import com.workshop.room.IRoomRepository
import com.workshop.room.Room
import com.workshop.room.create
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

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

        // CREATE ROOM
        post("/create-room") {
            val room = call.parameters["room"]
            val moderator = call.parameters["moderator"]
            if (room == null || moderator == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val newRoom = Room(
                name = room,
                moderator = moderator
            )

            val roomId = roomRepository.createRoom(
                newRoom
            )

            val newPlayer = Player(
                name = moderator,
                roomId = roomId
            )

            playerRepository.createPlayer(player = newPlayer)
            sendResult(roomRepository, roomId, newRoom)
        }

        // JOIN ROOM
        post("/join-room") {
            val room = call.parameters["room"]
            val player = call.parameters["player"]

            if (room == null || player == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val (roomId, roomModel) = roomRepository.getRoom(room) ?: run {
                call.respond(HttpStatusCode.NotFound)
                return@post
            }

            val newPlayer = Player(
                name = player,
                roomId = roomId
            )

            playerRepository.createPlayer(player = newPlayer)
            sendResult(roomRepository, roomId, roomModel)
        }

        // REMOVER PLAYER
        post("/remove-player") {
            val room = call.parameters["room"]
            val moderator = call.parameters["player"]
            val player = call.receiveText()

            if (room == null || moderator == null || player.isEmpty()) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val (roomId, roomModel) = roomRepository.getRoom(room) ?: run {
                call.respond(HttpStatusCode.NotFound)
                return@post
            }

            roomRepository.getRoomByModerator(room, moderator)?.let { (roomId, _) ->
                val result = playerRepository.deletePlayer(player, roomId)
                if (result) {
                    sendResult(roomRepository, roomId, roomModel)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            } ?: run {
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        // GET ROOM
        get("/room") {
            val room = call.parameters["room"]
            if (room == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            roomRepository.getRoom(room)?.let { (roomId, roomModel) ->
                sendResult(roomRepository, roomId, roomModel)
            } ?: run {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        // SEND PLAYER VOTE
        post("/sendvote") {
            val room = call.parameters["room"]
            val player = call.parameters["player"]
            val point = call.receiveText()

            if (room == null || player == null || point.isEmpty()) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val (roomId, roomModel) = roomRepository.getRoom(room) ?: run {
                call.respond(HttpStatusCode.NotFound)
                return@post
            }

            playerRepository.getPlayer(player, roomId)?.let { (playerId, _) ->
                val result = playerRepository.updatePlayer(playerId, point)
                if (result) {
                    sendResult(roomRepository, roomId, roomModel)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            } ?: run {
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        // RESET ALL PLAYERS VOTE
        post("/reset-votes") {
            val room = call.parameters["room"]
            val moderator = call.parameters["player"]

            if (room == null || moderator == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val (roomId, roomModel) = roomRepository.getRoom(room) ?: run {
                call.respond(HttpStatusCode.NotFound)
                return@post
            }

            roomRepository.getRoomByModerator(room, moderator)?.let { (roomId, _) ->
                playerRepository.resetVotes(roomId)
                sendResult(roomRepository, roomId, roomModel)
            } ?: run {
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        // Static plugin. Try to access `/static/index.html`
        staticResources("/static", "static")
    }
}

private suspend fun RoutingContext.sendResult(
    roomRepository: IRoomRepository,
    roomId: Int,
    roomModel: Room
) {
    val localPlayers = roomRepository.getPlayersByRoomId(roomId)
    val roomPlayer = create(
        room = roomModel,
        players = localPlayers
    )
    call.respond(roomPlayer)
}
