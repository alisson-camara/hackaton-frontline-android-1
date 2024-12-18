package com.workshop.player

import com.workshop.db.PlayerDAO

class PlayerRepository : IPlayerRepository {
    override fun createPlayer(player: Player) {
        PlayerDAO.new {
            name = player.name
            point = "?"
        }
    }
}