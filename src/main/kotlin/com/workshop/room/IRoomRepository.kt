package com.workshop.room

import com.workshop.player.Player

interface IRoomRepository {
    suspend fun createRoom(room: Room): Int
    suspend fun getRoom(roomName: String): Pair<Int, Room>?
    suspend fun getRoom(roomId: Int): Pair<Int,Room>?
    suspend fun getPlayersByRoomId(roomId: Int): List<Player>
}