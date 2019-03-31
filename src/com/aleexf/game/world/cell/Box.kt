package com.aleexf.game.world.cell

import java.awt.Image

import com.aleexf.graphics.drawer.TextureManager

class Box(x: Int, y: Int) : Object(x, y, true, 9) {
    override var tSizeX: Int = 64
    override var tSizeY: Int = 64
    override var rSizeX: Int = 32
    override var rSizeY: Int = 32
    override var texture: Image = TextureManager.iBox
    override val priority: Int = 1
}