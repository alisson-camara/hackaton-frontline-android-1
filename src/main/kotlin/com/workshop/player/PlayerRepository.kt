package com.workshop.player

import com.workshop.db.PlayerDAO
import com.workshop.db.suspendTransaction

class PlayerRepository : IPlayerRepository {
    override fun createPlayer(player: Player): Unit = suspendTransaction {
        PlayerDAO.new {
            name = player.name
            point = "?"
        }
    }
}