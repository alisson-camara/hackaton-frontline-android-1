package com.workshop.player

interface IPlayerRepository {
    suspend fun createPlayer(player: Player)
    suspend fun deletePlayer(playerName: String, roomId: Int): Boolean
    suspend fun getPlayer(playerName: String, roomId: Int): Pair<Int, Player>?
    suspend fun updatePlayer(playerId: Int, newPoint: String): Boolean
}