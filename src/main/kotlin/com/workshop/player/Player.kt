package com.workshop.player

import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val name: String,
    val point: String = "?",
    val roomId: Int
)
