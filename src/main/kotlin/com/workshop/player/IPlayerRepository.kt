package com.workshop.player

interface IPlayerRepository {
    suspend fun createPlayer(player: Player)
    suspend fun deletePlayer(playerName: String, roomId: Int): Boolean
}