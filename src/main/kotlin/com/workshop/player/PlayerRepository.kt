package com.workshop.player

import com.workshop.db.PlayerDAO
import com.workshop.db.PlayerTable
import com.workshop.db.suspendTransaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class PlayerRepository(
) : IPlayerRepository {
    override suspend fun createPlayer(player: Player): Unit = suspendTransaction {
        PlayerDAO.new {
            name = player.name
            point = "?"
            roomId = player.roomId
        }
    }

    override suspend fun deletePlayer(playerName: String, roomId: Int): Boolean =
        suspendTransaction {
            val rowsDeleted = PlayerTable.deleteWhere {
                (PlayerTable.room eq roomId) and (PlayerTable.name eq playerName)
            }
            rowsDeleted == 1
        }

    override suspend fun getPlayer(playerName: String, roomId: Int): Pair<Int, Player>? =
        suspendTransaction {
            val room = PlayerDAO
                .find { (PlayerTable.name eq playerName) and (PlayerTable.room eq roomId) }
                .limit(1)
                .firstOrNull()
            room?.let { safeRoom ->
                return@suspendTransaction Pair(safeRoom.id.value, playerDaoToModel(safeRoom))
            } ?: return@suspendTransaction null
        }

    override suspend fun updatePlayer(playerId: Int, newPoint: String): Boolean =
        suspendTransaction {
            return@suspendTransaction PlayerDAO
                .findByIdAndUpdate(playerId) { player ->
                    player.point = newPoint
                }?.point == newPoint
        }

    override suspend fun resetVotes(roomId: Int) {
        suspendTransaction {
            PlayerTable.update ( {PlayerTable.room eq roomId} ) {
                it[point] = "?"
            }
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