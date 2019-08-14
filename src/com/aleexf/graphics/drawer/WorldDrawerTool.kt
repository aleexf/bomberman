package com.aleexf.graphics.drawer

import java.awt.Graphics
import javax.swing.JPanel

import com.aleexf.game.world.GameWorld

class WorldDrawerTool(var world: GameWorld) : JPanel() {
    init {
        this.isFocusable = true
    }
    override fun paint(graph: Graphics) {
        world.objects.sortedBy { it.priority }.forEach { it.drawIt(graph) }
    }
}
