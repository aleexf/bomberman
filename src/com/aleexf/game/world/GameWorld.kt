package com.aleexf.game.world

import com.aleexf.game.Direction
import java.io.BufferedReader
import java.io.FileReader

import com.aleexf.game.Player
import com.aleexf.game.world.cell.*
import com.aleexf.net.client.Connection

class GameWorld(val nick:String, val ip:String, val connection:Connection) {
    val rows:Int
    val cols:Int
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
        loadWorld(connection.getMessage().toInt())
        val worldUpdater:WorldUpdater = WorldUpdater(this)
        worldUpdater.start()
    }
    fun anyObject(collision: Int, x: Int, y: Int): Boolean = usedGrid.contains(listOf(x, y))
    fun loadWorld(worldId:Int) {
        blocks.clear();
        boxes.clear();
        bombs.clear();
        explosion.clear();
        usedGrid.clear();
        val LevelReader = BufferedReader(FileReader(".\\data\\levels\\$worldId.level"))
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
                        spawnCrd[row[j].toInt()] = listOf(i, j)
                    }
                }
            }
        }
    }
}


class WorldUpdater(val world:GameWorld):Thread() {
    override fun run() {
        while (true) {
            val msg:List<String> = world.connection.getMessage().split(' ')
            when (msg[0]) {
                "player" -> {
                    when (msg[1]) {
                        "connected" -> world.players.add(Player(0, 0, msg[2], msg[3].toInt(), world))
                        "disconnected" -> {
                            val playerId = msg[2].toInt()
                            for (player in world.players) {
                                if (player.playerId == playerId) {
                                    world.players.remove(player)
                                    break
                                }
                            }
                        }
                    }
                }
                "game" -> {
                    when (msg[1]) {
                        "reload_map" -> world.loadWorld(msg[1].toInt())
                        "start" -> {
                            for (player in world.players) {
                                player.alive = true
                                player.x = world.spawnCrd[player.playerId]?.get(0) ?: -1
                                player.y = world.spawnCrd[player.playerId]?.get(0) ?: -1
                            }
                        }
                    }
                }
                "action" -> {
                    when (msg[1]) {
                        "move" -> {
                            val playerId = msg[2].toInt()
                            for (player in world.players) {
                                if (player.playerId == playerId) {
                                    player.move(Direction.valueOf(msg[3]))
                                }
                            }
                        }
                        "place_bomb" -> {
                            val playerId = msg[2].toInt()
                            for (player in world.players) {
                                if (player.playerId == playerId) {
                                    val bomb = player.placeBomb()
                                    world.bombs.add(bomb)
                                    // Wait for bomb explosion //
                                }
                            }
                        }
                        "picked_bonus" -> {
                            // Will be added soon //
                        }
                    }
                }
            }
        }
    }
}
