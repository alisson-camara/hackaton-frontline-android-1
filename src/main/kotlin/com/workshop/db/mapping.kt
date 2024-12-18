package com.workshop.db

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object RoomTable : IntIdTable("room") {
    val name = varchar("name", 50)
    val currentTask = varchar("current_task", 50)
    val moderator = varchar("moderator", 50)
}

object PlayerTable: IntIdTable("player") {
    val name = varchar("name", 50)
    val point = varchar("current_task", 50)
    val room = integer("room_id")
}

class RoomDAO(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<RoomDAO>(RoomTable)

    var name by RoomTable.name
    var currentTask by RoomTable.currentTask
    var moderator by RoomTable.moderator
}

class PlayerDAO(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<PlayerDAO>(PlayerTable)

    var name by PlayerTable.name
    var point by PlayerTable.point
    var roomId by PlayerTable.room
}

suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)