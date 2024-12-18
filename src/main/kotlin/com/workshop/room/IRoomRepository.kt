package com.workshop.room

import com.workshop.player.Player

interface IRoomRepository {
    suspend fun createRoom(room: Room): Int
    suspend fun getRoom(roomName: String): Room?
    suspend fun getRoom(roomId: Int): Room?
    suspend fun getPlayersByRoomId(roomId: Int): List<Player>
}