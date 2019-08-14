package com.aleexf.game.world.cell

import java.awt.Image
import javax.swing.Timer

import com.aleexf.graphics.drawer.Animationable
import com.aleexf.graphics.drawer.TextureManager

class Explosion(x: Int, y: Int) :
        Object(x, y, false, 0),
        Animationable
{
    override var tSizeX: Int = 64
    override var tSizeY: Int = 64
    override var rSizeX: Int = 32
    override var rSizeY: Int = 32
    override var texture: Image = TextureManager.iExplosion[0]
    override val priority: Int = 10

    override var animState: Long? = 0
    override val animDelay: Long? = 30
    override val creationTime = System.currentTimeMillis()


    override fun updateAnimState() {
        if (animState == null || animState!! >= 31) {
            animState = null
            return
        }
        animState = (System.currentTimeMillis() - creationTime) / animDelay!!
        texture = TextureManager.iExplosion[animState!!.toInt()]
    }
}