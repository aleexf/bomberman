package com.aleexf.game.world.cell

import java.awt.Image

import com.aleexf.game.Player
import com.aleexf.graphics.drawer.TextureManager
import java.awt.Graphics

class Bomb(
        x: Int,
        y: Int,
        val delay: Long,
        val owner: Player,
        val explosionLen: Int
): Object(x, y, true, 2) {

    var realX = x * 32 + 16
    var realY = y * 32 + 16
    var speedX = 0
    var speedY = 0

    override var tSizeX: Int = 50
    override var tSizeY: Int = 50
    override var rSizeX: Int = 24
    override var rSizeY: Int = 24
    override val priority: Int = 0
    override var texture: Image = TextureManager.iBomb

    override fun drawIt(graph: Graphics) {
        graph.drawImage(texture,
                realY - rSizeY / 2, realX - rSizeX / 2,
                realY + rSizeY / 2, realX + rSizeX / 2,
                0, 0,
                tSizeY, tSizeY,
                null
        )
    }
}