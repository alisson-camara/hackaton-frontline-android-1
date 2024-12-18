package com.workshop

import com.workshop.player.IPlayerRepository
import com.workshop.player.Player
import com.workshop.room.IRoomRepository
import com.workshop.room.Room
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
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.transactions.transaction

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

            launch {
                val roomModel = Room(
                    name = room,
                    moderator = moderator
                )

                roomRepository.createRoom(
                    roomModel
                )
                playerRepository.createPlayer(
                    Player(
                        name = moderator,
                        room = roomModel
                    )
                )
            }

            val selectedRoom = roomRepository.getRoom(room)
            if (selectedRoom != null) {
                call.respond(selectedRoom)
            } else {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
        }

        // Static plugin. Try to access `/static/index.html`
        staticResources("/static", "static")
    }
}
