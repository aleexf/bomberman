package com.aleexf.game.world

import com.aleexf.game.Player
import com.aleexf.game.Direction
import com.aleexf.game.world.cell.Bonus
import com.aleexf.net.client.Connection

class WorldUpdater(val world:GameWorld, val connection: Connection?, val hostUpdater: HostWorldControl?):Thread() {
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
                    "reload_map" -> {
                        world.loadWorld(msg[2].toInt())
                        hostUpdater?.generateBonuses()
                    }
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
                    "appeared_bonus" -> {
                        val px = msg[2].toInt()
                        val py = msg[3].toInt()
                        val bType = msg[4].toInt()
                        world.objects.add(Bonus(px, py, bType))
                        world.usedGrid.add(Pair(1, Pair(px, py)))
                    }
                    "picked_bonus" -> {
                        val playerId = msg[2].toInt()
                        val px = msg[3].toInt()
                        val py = msg[4].toInt()
                        world.findPlayerById(playerId).applyBonus(
                                world.objects.first { it is Bonus && it.x == px && it.y == py } as Bonus
                        )
                        world.objects.removeIf{
                            if (it is Bonus && it.x == px && it.y == py) {
                                world.usedGrid.remove(Pair(it.collision, Pair(it.x, it.y)))
                                true
                            } else {
                                false
                            }
                        }
                    }
                }
            }
        }
    }
}