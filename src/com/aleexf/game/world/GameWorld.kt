package com.aleexf.game.world

import java.io.BufferedReader
import java.io.FileReader

import com.aleexf.game.Player
import com.aleexf.game.world.cell.*
import com.aleexf.logging.Logger


class GameWorld(val nick:String) {
    val rows:Int = 15
    val cols:Int = 15
    var worldId:Int = 0
    val spawnCrd: MutableMap<Int, List<Int>> = mutableMapOf()
    val objects: MutableList<Object> = mutableListOf()
    val explosion: MutableList<Explosion> = mutableListOf()
    val usedGrid: MutableList<Pair<Int, Pair<Int,Int>>> = mutableListOf()
    val players: MutableList<Player> = mutableListOf()
    fun findPlayerById(playerId: Int):Player {
        if (players.count{it.playerId == playerId} == 0)
            throw NullPointerException("Player not found")
        return players.first {it.playerId == playerId}
    }
    fun anyObject(collision: Int, x: Int, y: Int): Boolean {
        return usedGrid.filter{it.first >= collision}.any{it.second == Pair(x, y)}
    }
    fun loadWorld(worldId:Int) {
        objects.clear();
        explosion.clear();
        usedGrid.clear();
        Logger.info(msg = "[${if (nick == "server") "Server" else "Client"}]: Loading levels/$worldId.level")
        val levelReader = BufferedReader(FileReader("./data/levels/$worldId.level"))
        for (i in 0..14) {
            val row: String = levelReader.readLine()
            for (j in 0..14) {
                if (row[j] == 'E') continue
                when (row[j]) {
                    'X' -> {
                        objects.add(Block(i, j))
                        usedGrid.add(Pair(objects.last().collision, Pair(i, j)))
                    }
                    'B' -> {
                        objects.add(Box(i, j))
                        usedGrid.add(Pair(objects.last().collision, Pair(i, j)))
                    }
                    else -> {
                        spawnCrd[row[j].toInt() - 48] = listOf(i * 32, j * 32)
                    }
                }
            }
        }
    }
}

