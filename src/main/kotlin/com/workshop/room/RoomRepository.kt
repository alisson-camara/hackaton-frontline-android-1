package com.workshop.room

import com.workshop.db.RoomDAO

class RoomRepository : IRoomRepository {
    override suspend fun createRoom(room: Room) {
        RoomDAO.new {
            name = room.name
            moderator = room.moderator
            currentTask = room.currentTask
        }
    }
}