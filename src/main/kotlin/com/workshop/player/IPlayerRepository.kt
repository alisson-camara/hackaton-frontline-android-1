package com.workshop.player

interface IPlayerRepository {
    suspend fun createPlayer(player: Player)
}