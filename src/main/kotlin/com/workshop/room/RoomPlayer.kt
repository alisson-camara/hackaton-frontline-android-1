package com.workshop.room

import com.workshop.player.Player
import kotlinx.serialization.Serializable

@Serializable
data class RoomPlayer(
    val room: Room,
    val players: List<Player>
)
