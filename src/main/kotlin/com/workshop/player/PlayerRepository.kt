package com.workshop.player

import com.workshop.db.PlayerDAO
import com.workshop.db.suspendTransaction
import com.workshop.room.IRoomRepository

class PlayerRepository(
    private val roomRepository: IRoomRepository
) : IPlayerRepository {
    override suspend fun createPlayer(player: Player): Unit = suspendTransaction {
        PlayerDAO.new {
            name = player.name
            point = "?"
            room = player.roomId
        }
    }
}