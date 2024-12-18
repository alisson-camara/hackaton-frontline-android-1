package com.workshop.room

import com.workshop.db.RoomDAO
import com.workshop.db.RoomTable
import com.workshop.db.TaskDAO
import com.workshop.db.TaskTable
import com.workshop.db.daoToModel
import com.workshop.db.suspendTransaction
import com.workshop.tasks.Priority
import com.workshop.tasks.Task
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

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

    private fun daoToModel(dao: RoomDAO) = Room(
        dao.name,
        dao.moderator
    )
}