package com.workshop.room

import com.workshop.db.RoomDAO
import com.workshop.db.suspendTransaction

class RoomRepository : IRoomRepository {
    override suspend fun createRoom(room: Room): Unit = suspendTransaction {
        RoomDAO.new {
            name = room.name
            moderator = room.moderator
            currentTask = room.currentTask
        }
    }
}