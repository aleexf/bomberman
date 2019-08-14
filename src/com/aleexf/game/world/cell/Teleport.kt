package com.aleexf.game.world.cell

import java.awt.Image

import com.aleexf.graphics.drawer.Animationable
import com.aleexf.graphics.drawer.TextureManager

class Teleport(x: Int, y: Int, val index: Int, var other: Teleport? = null):
        Object(x, y, false, 1),
        Animationable
{
    override var tSizeX: Int = 64
    override var tSizeY: Int = 64
    override var rSizeX: Int = 32
    override var rSizeY: Int = 32
    override var texture: Image = TextureManager.iBox
    override val priority: Int = 1

    override var animState: Long? = 0
    override val animDelay: Long = 0
    override val creationTime: Long? = System.currentTimeMillis()

    override fun updateAnimState() {
        
    }
}
