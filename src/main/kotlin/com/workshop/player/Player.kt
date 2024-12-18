package com.workshop.player

import com.workshop.room.Room
import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val name: String,
    val point: String = "?",
    val room: Room
)
