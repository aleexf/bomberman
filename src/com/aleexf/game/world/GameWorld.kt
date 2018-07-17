package com.aleexf.game.world

import com.aleexf.game.Player
import com.aleexf.game.world.cell.*


class GameWorld(val nick:String) {
    val rows:Int = 15
    val cols:Int = 15
    var worldId:Int = 0
    val spawnCrd: MutableMap<Int, List<Int>> = mutableMapOf()
    val objects: MutableList<Object> = mutableListOf()
    val explosion: MutableList<Explosion> = mutableListOf()
    val players: MutableList<Player> = mutableListOf()
    fun findPlayerById(playerId: Int):Player {
        if (players.count{it.playerId == playerId} == 0)
            throw NullPointerException("Player not found")
        return players.first {it.playerId == playerId}
    }
    fun anyObject(collision: Int, x: Int, y: Int): Boolean {
        return objects.filter{it.collision >= collision}.any{Pair(it.x, it.y) == Pair(x, y)}
            || players.filter{it.collision >= collision && it.alive}.any{Pair(it.x, it.y) == Pair(x, y)}
    }
    fun loadWorld(worldId:Int) = loadWorld(worldId, this)
    fun onServer() = nick == "server"
}

