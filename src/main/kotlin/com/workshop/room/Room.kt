package com.workshop.room

import kotlinx.serialization.Serializable

@Serializable
data class Room(
    val name: String,
    val moderator: String,
    val currentTask: String = "Task 1"
)
