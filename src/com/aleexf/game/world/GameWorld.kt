package com.aleexf.game.world

import java.io.BufferedReader
import java.io.FileReader

import com.aleexf.game.Player
import com.aleexf.game.world.cell.*
import com.aleexf.net.client.Connection


class GameWorld(val nick:String, val connection:Connection) {
    val rows:Int
    val cols:Int
    val worldId:Int
    val spawnCrd: MutableMap<Int, List<Int>> = mutableMapOf()
    val blocks: MutableList<Block> = mutableListOf()
    val boxes: MutableSet<Box> = mutableSetOf()
    val bombs: MutableSet<Bomb> = mutableSetOf()
    val explosion: MutableSet<Explosion> = mutableSetOf()
    val usedGrid: MutableSet<List<Int>> = mutableSetOf()
    val players: MutableSet<Player> = mutableSetOf()
    init {
        rows = 15
        cols = 15
        connection.sendMessage("get_world_id")
        worldId = connection.getMessage().toInt()
        loadWorld(worldId)
        val worldUpdater:WorldUpdater = WorldUpdater(this)
        worldUpdater.start()
    }
    fun findPlayerById(playerId: Int):Player {
        if (players.count{it.playerId == playerId} == 0)
            throw NullPointerException("Player not found")
        return players.filter{it.playerId == playerId}.first()
    }
    fun anyObject(collision: Int, x: Int, y: Int): Boolean = usedGrid.contains(listOf(x, y))
    fun loadWorld(worldId:Int) {
        blocks.clear();
        boxes.clear();
        bombs.clear();
        explosion.clear();
        usedGrid.clear();
        val LevelReader = BufferedReader(FileReader("./data/levels/$worldId.level"))
        for (i in 0..14) {
            val row:String = LevelReader.readLine()
            for (j in 0..14) {
                if (row[j] == 'E') continue
                when (row[j]) {
                    'X' -> {
                        blocks.add(Block(i, j))
                        usedGrid.add(listOf(i, j))
                    }
                    'B' -> {
                        boxes.add(Box(i, j))
                        usedGrid.add(listOf(i, j))
                    }
                    else -> {
                        spawnCrd[row[j].toInt()-48] = listOf(i*32, j*32)
                    }
                }
            }
        }
    }
}

