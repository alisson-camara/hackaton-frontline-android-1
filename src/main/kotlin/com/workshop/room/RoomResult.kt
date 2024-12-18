package com.workshop.room

import com.workshop.player.Player
import kotlinx.serialization.Serializable

@Serializable
data class RoomResult (
    val name: String,
    val currentTask: String,
    val moderator: String,
    val players: List<Player>
)

fun create(room: Room, players: List<Player>): RoomResult {
    return RoomResult(
        room.name,
        room.currentTask,
        room.moderator,
        players
    )
}
