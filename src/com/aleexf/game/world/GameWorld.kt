package com.aleexf.game.world

import java.io.BufferedReader
import java.io.FileReader

import com.aleexf.game.Player
import com.aleexf.game.world.cell.*
import com.aleexf.logging.Logger


class GameWorld(val nick:String) {
    val rows:Int
    val cols:Int
    var worldId:Int = 0
    val spawnCrd: MutableMap<Int, List<Int>> = mutableMapOf()
    val blocks: MutableList<Block> = mutableListOf()
    val boxes: MutableSet<Box> = mutableSetOf()
    val bombs: MutableSet<Bomb> = mutableSetOf()
    val explosion: MutableSet<Explosion> = mutableSetOf()
    val usedGrid: MutableSet<Pair<Int, Pair<Int,Int>>> = mutableSetOf()
    val players: MutableSet<Player> = mutableSetOf()
    init {
        rows = 15
        cols = 15
    }
    fun findPlayerById(playerId: Int):Player {
        if (players.count{it.playerId == playerId} == 0)
            throw NullPointerException("Player not found")
        return players.first {it.playerId == playerId}
    }
    fun anyObject(collision: Int, x: Int, y: Int): Boolean {
        return usedGrid.filter{it.first >= collision}.any{it.second == Pair(x, y)}
    }
    fun loadWorld(worldId:Int) {
        blocks.clear();
        boxes.clear();
        bombs.clear();
        explosion.clear();
        usedGrid.clear();
        val levelReader = BufferedReader(FileReader("./data/levels/$worldId.level"))
        for (i in 0..14) {
            val row: String = levelReader.readLine()
            for (j in 0..14) {
                if (row[j] == 'E') continue
                when (row[j]) {
                    'X' -> {
                        blocks.add(Block(i, j))
                        usedGrid.add(Pair(blocks.last().collision, Pair(i, j)))
                    }
                    'B' -> {
                        boxes.add(Box(i, j))
                        usedGrid.add(Pair(boxes.last().collision, Pair(i, j)))
                    }
                    else -> {
                        spawnCrd[row[j].toInt() - 48] = listOf(i * 32, j * 32)
                    }
                }
            }
        }
    }
}

