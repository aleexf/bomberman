package com.aleexf.game.drawer

import java.awt.Graphics
import javax.swing.JPanel

import com.aleexf.game.Direction
import com.aleexf.game.world.GameWorld

class WorldDrawerTool(var world: GameWorld) : JPanel() {
    init {
        this.isFocusable = true
    }
    override fun paint(graph: Graphics) {
        /* Blocks */
        for (x in 0..14) {
            for (y in 0..14) {
                graph.drawImage(iFloor,
                        y*32, x*32,
                        y*32+32, x*32+32,
                        0, 0,
                        32, 32,
                        null
                )
            }
        }
        for (block in world.blocks) {
            graph.drawImage(iBlock,
                    block.y*32, block.x*32,
                    block.y*32+32, block.x*32+32,
                    0, 0,
                    64, 64,
                    null)
        }
        for (box in world.boxes) {
            graph.drawImage(iBox,
                    box.y*32, box.x*32,
                    box.y*32+32, box.x*32+32,
                    0, 0,
                    64, 64,
                    null)
        }
        for (bomb in world.bombs) {
            graph.drawImage(when(bomb.delay) {
                                3000L -> iBomb
                                else -> iToxicBomb
                            },
                    bomb.y*32, bomb.x*32,
                    bomb.y*32+32, bomb.x*32+32,
                    0, 0,
                    60, 60,
                    null
            )
        }
        for (player in world.players) {
            if (!player.alive) continue
            val imgId = when(player.direction) {
                Direction.UP -> 0
                Direction.DOWN -> 3
                Direction.LEFT -> 6
                Direction.RIGHT -> 9
            }
            graph.drawImage(iPlayer[player.playerId][imgId+player.animType],
                    player.y, player.x,
                    player.y+32, player.x+32,
                    0, 0,
                    64, 64,
                    null
            )
        }
        for (explosion in world.explosion) {
            graph.drawImage(iExplosion[explosion.animType],
                    explosion.y*32, explosion.x*32,
                    explosion.y*32+32, explosion.x*32+32,
                    0, 0,
                    64, 64,
                    null
            )
        }
    }
}
