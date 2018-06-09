package com.aleexf.game.world

import com.aleexf.game.Player
import com.aleexf.game.Direction
import com.aleexf.net.client.Connection

class WorldUpdater(val world:GameWorld, val connection: Connection?):Thread() {
    override fun run() {
        while (true) {
            this.updateWorld(connection!!.getMessage())
        }
    }
    fun updateWorld(message:String) {
        val msg = message.split(' ')
        when (msg[0]) {
            "player" -> {
                when (msg[1]) {
                    "connected" -> world.players.add(Player(0, 0, msg[2], msg[3].toInt(), world))
                    "disconnected" -> {
                        val playerId = msg[2].toInt()
                        world.players.remove(world.findPlayerById(playerId))
                    }
                }
            }
            "game" -> {
                when (msg[1]) {
                    "reload_map" -> world.loadWorld(msg[2].toInt())
                    "start" -> {
                        for (player in world.players) {
                            player.defaultParams()
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
                        world.findPlayerById(playerId).move(Direction.valueOf(msg[3]))
                    }
                    "place_bomb" -> {
                        val playerId = msg[2].toInt()
                        val detonator = Detonator(world, world.findPlayerById(playerId))
                        detonator.start()
                    }
                    "picked_bonus" -> {
                        // Will be added soon //
                    }
                }
            }
        }
    }
}