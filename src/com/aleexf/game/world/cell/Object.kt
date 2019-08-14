package com.aleexf.game.world.cell

import com.aleexf.graphics.drawer.Drawable
import java.awt.Graphics

abstract class Object(
    var x: Int,
    var y: Int,
    val breakByExplosion: Boolean,
    var collision: Int
) : Drawable {
    override fun drawIt(graph: Graphics) {
        graph.drawImage(
                texture,
                y * 32, x * 32,
                y * 32 + rSizeY, x * 32 + rSizeX,
                0, 0,
                tSizeY, tSizeX,
                null
        )
    }
}
