package com.aleexf.game.world

import com.aleexf.config.Config
import com.aleexf.game.Player
import com.aleexf.game.world.cell.*

class GameWorld(val onServer: Boolean) {
    val rows = Config.GameFieldRows
    val cols = Config.GameFieldCols
    var worldId = 0
    val spawnCrd: MutableMap<Int, List<Int>> = mutableMapOf()
    val objects: MutableList<Object> = mutableListOf()
    val players: MutableList<Player> = mutableListOf()
    private val worldLoader = WorldLoader(this)

    fun findPlayerById(playerId: Int): Player {
        return players.firstOrNull { it.playerId == playerId }
                ?: throw java.lang.NullPointerException("Player not found")
    }
    fun anyObject(collision: Int, x: Int, y: Int): Boolean {
        return objects.filter { it.collision >= collision }
                      .any { it.x == x && it.y == y }
            || players.filter { it.collision >= collision && it.alive }
                      .any { it.x == x && it.y == y }
    }
    fun loadWorld(worldId: Int) = worldLoader.loadWorld(worldId)
}

