package com.aleexf.graphics.drawer

import java.awt.Graphics
import javax.swing.JPanel

import com.aleexf.game.world.GameWorld
import com.aleexf.game.world.cell.Explosion

class WorldDrawerTool(var world: GameWorld) : JPanel() {
    init {
        this.isFocusable = true
    }
    override fun paint(graph: Graphics) {
        for (i in 0 until world.rows) {
            for (j in 0 until world.cols) {
                graph.drawImage(TextureManager.iFloor,
                        j * 32, i * 32,
                        j * 32 + 32, i * 32 + 32,
                        0, 0,
                        32, 32,
                        null
                )
            }
        }
        world.objects.sortedBy { it.priority }.forEach { it.drawIt(graph) }
        world.players.filter { it.alive }.forEach { it.drawIt(graph) }

        world.objects.removeAll { it is Explosion && it.animState == null }
    }
}
