package com.workshop.room

interface IRoomRepository {
    suspend fun createRoom(room: Room)
}