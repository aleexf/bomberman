package com.aleexf.graphics.drawer

import java.lang.Math.min
import java.awt.Graphics
import javax.swing.JPanel

import com.aleexf.game.Direction
import com.aleexf.game.world.cell.*
import com.aleexf.game.world.GameWorld

class WorldDrawerTool(var world: GameWorld) : JPanel() {
    init {
        this.isFocusable = true
    }
    override fun paint(graph: Graphics) {

    }
}
