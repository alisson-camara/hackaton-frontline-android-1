package com.workshop.player

import com.workshop.db.PlayerDAO
import com.workshop.db.suspendTransaction

class PlayerRepository(
) : IPlayerRepository {
    override suspend fun createPlayer(player: Player): Unit = suspendTransaction {
        PlayerDAO.new {
            name = player.name
            point = "?"
            roomId = player.roomId
        }
    }

    companion object {
        fun playerDaoToModel(dao: PlayerDAO) = Player(
            dao.name,
            dao.point,
            dao.roomId
        )
    }
}