package com.workshop.room

import com.workshop.db.PlayerDAO
import com.workshop.db.PlayerTable
import com.workshop.db.RoomDAO
import com.workshop.db.RoomTable
import com.workshop.db.suspendTransaction
import com.workshop.player.Player
import com.workshop.player.PlayerRepository.Companion.playerDaoToModel

class RoomRepository : IRoomRepository {
    override suspend fun createRoom(room: Room): Int = suspendTransaction {
        return@suspendTransaction RoomDAO.new {
            name = room.name
            moderator = room.moderator
            currentTask = room.currentTask
        }.id.value
    }

    override suspend fun getRoom(roomName: String): Room? = suspendTransaction {
        RoomDAO
            .find { (RoomTable.name eq roomName) }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun getRoom(roomId: Int): Room? = suspendTransaction {
        RoomDAO
            .find { (RoomTable.id eq roomId) }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun getPlayersByRoomId(roomId: Int): List<Player> = suspendTransaction {
        PlayerDAO
            .find { (PlayerTable.room eq roomId) }
            .map(::playerDaoToModel)
    }

    private fun daoToModel(dao: RoomDAO) = Room(
        dao.name,
        dao.moderator
    )
}