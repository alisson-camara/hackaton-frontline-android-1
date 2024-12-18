package com.workshop.player

import com.workshop.db.PlayerDAO
import com.workshop.db.PlayerTable
import com.workshop.db.TaskTable
import com.workshop.db.suspendTransaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere

class PlayerRepository(
) : IPlayerRepository {
    override suspend fun createPlayer(player: Player): Unit = suspendTransaction {
        PlayerDAO.new {
            name = player.name
            point = "?"
            roomId = player.roomId
        }
    }

    override suspend fun deletePlayer(playerName: String, roomId: Int): Boolean = suspendTransaction {
        val rowsDeleted = PlayerTable.deleteWhere {
            (PlayerTable.room eq roomId) and (PlayerTable.name eq playerName)
        }
        rowsDeleted == 1
    }

    companion object {
        fun playerDaoToModel(dao: PlayerDAO) = Player(
            dao.name,
            dao.point,
            dao.roomId
        )
    }
}