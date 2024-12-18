package com.workshop.room

import com.workshop.db.suspendTransaction

interface IRoomRepository {
    suspend fun createRoom(room: Room): Int
    suspend fun getRoom(roomName: String): Room?
    suspend fun getRoom(roomId: Int): Room?
}