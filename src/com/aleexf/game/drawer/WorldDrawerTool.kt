package com.aleexf.game.drawer

import java.lang.Math.min
import java.awt.Graphics
import javax.swing.JPanel

import com.aleexf.game.Direction
import com.aleexf.game.world.cell.*
import com.aleexf.game.world.GameWorld
import com.aleexf.game.drawer.TextureManager

class WorldDrawerTool(var world: GameWorld) : JPanel() {
    init {
        this.isFocusable = true
    }
    override fun paint(graph: Graphics) {
        /* Blocks */
        for (x in 0..14) {
            for (y in 0..14) {
                graph.drawImage(TextureManager.iFloor,
                        y*32, x*32,
                        y*32+32, x*32+32,
                        0, 0,
                        32, 32,
                        null
                )
            }
        }
        for (obj in world.objects.asReversed()) {
            when (obj) {
                is Block -> {
                    graph.drawImage(TextureManager.iBlock,
                            obj.y*32, obj.x*32,
                            obj.y*32+32, obj.x*32+32,
                            0, 0,
                            64, 64,
                            null)
                }
                is Box -> {
                    graph.drawImage(TextureManager.iBox,
                            obj.y*32, obj.x*32,
                            obj.y*32+32, obj.x*32+32,
                            0, 0,
                            64, 64,
                            null)
                }
                is Bomb -> {
                    graph.drawImage(when(obj.delay) {
                        3000L -> TextureManager.iBomb
                        else -> TextureManager.iToxicBomb
                    },
                            obj.realY-12, obj.realX-12,
                            obj.realY+12, obj.realX+12,
                            0, 0,
                            50, 50,
                            null
                    )
                }
                is Bonus -> {
                    graph.drawImage(
                            TextureManager.iBonus[min(4, obj.bType)],
                            obj.y*32, obj.x*32,
                            obj.y*32+32, obj.x*32+32,
                            0, 0,
                            32, 32,
                            null
                    )
                }
                is HeavyBox -> {
                    graph.drawImage(
                            TextureManager.iHeavyBox,
                            obj.y*32, obj.x*32,
                            obj.y*32+32, obj.x*32+32,
                            0, 0,
                            64, 64,
                            null
                    )
                }
            }
        }
        for (player in world.players) {
            if (!player.alive) continue
            val imgId = when(player.direction) {
                Direction.UP -> 0
                Direction.DOWN -> 3
                Direction.LEFT -> 6
                Direction.RIGHT -> 9
            }
            graph.drawImage(TextureManager.iPlayer[player.playerId][imgId+player.animType],
                    player.y-16, player.x-16,
                    player.y+16, player.x+16,
                    0, 0,
                    64, 64,
                    null
            )
        }
        for (explosion in world.explosion) {
            graph.drawImage(TextureManager.iExplosion[explosion.animType],
                    explosion.y*32, explosion.x*32,
                    explosion.y*32+32, explosion.x*32+32,
                    0, 0,
                    64, 64,
                    null
            )
        }
    }
}
