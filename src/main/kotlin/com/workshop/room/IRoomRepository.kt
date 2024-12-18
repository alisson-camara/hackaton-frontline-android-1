package com.workshop.room

interface IRoomRepository {
    suspend fun createRoom(room: Room)

    suspend fun getRoom(roomName: String): Room?
}