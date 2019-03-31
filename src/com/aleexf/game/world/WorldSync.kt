package com.aleexf.game.world

import com.aleexf.game.Player
import com.aleexf.game.Direction
import com.aleexf.game.world.cell.Bomb
import com.aleexf.game.world.cell.Bonus
import com.aleexf.game.world.cell.Teleport
import com.aleexf.game.sound.Sounds
import com.aleexf.game.sound.Player.playSound
import com.aleexf.net.client.Connection

class WorldSync(
        val world:GameWorld,
        val connection: Connection?,
        private val hostUpdater: HostWorldControl?
) : Thread() {
    override fun run() {
        while (true) {
            this.updateWorld(connection!!.getMessage())
        }
    }
    fun updateWorld(message: String) {
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
                            player.resetStats()
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
                    }
                    "picked_bonus" -> {
                        val playerId = msg[2].toInt()
                        if (playerId == connection?.localId) {
                            playSound(Sounds.PICKUP)
                        }
                        val px = msg[3].toInt()
                        val py = msg[4].toInt()
                        world.findPlayerById(playerId).applyBonus(
                                world.objects.first { it is Bonus && it.x == px && it.y == py } as Bonus
                        )
                        world.objects.removeIf { it is Bonus && it.x == px && it.y == py  }
                    }
                    "change_collision" -> {
                        val px = msg[2].toInt()
                        val py = msg[3].toInt()
                        world.objects.find { it is Bomb && it.collision == 2 && it.x == px && it.y ==  py }
                                     ?.collision = 3
                    }
                    "teleport" -> {
                        val player = world.findPlayerById(msg[2].toInt())
                        val tpx = msg[3].toInt()
                        val tpy = msg[4].toInt()
                        val otherTeleport = (world.objects
                                .first {it is Teleport && it.x == tpx && it.y == tpy} as Teleport)
                                .other!!
                        player.x = otherTeleport.x * 32 + 16
                        player.y = otherTeleport.y * 32 + 16
                    }
                }
            }
        }
    }
}