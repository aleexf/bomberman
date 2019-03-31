package com.aleexf.game.world.cell

import java.awt.Image

import com.aleexf.graphics.drawer.TextureManager

class Bonus(x: Int, y: Int, val bType: Int) : Object(x, y, true, 1) {
    override var tSizeX: Int = 32
    override var tSizeY: Int = 32
    override var rSizeX: Int = 32
    override var rSizeY: Int = 32
    override val priority: Int = 0
    override var texture: Image = TextureManager.iBonus[Math.min(4, bType)]

    var speedBoost: Int = 0
    var bombBoost: Int = 0
    var explosionLen: Int = 0
    var delayBoost = 0
    var shuffleKeys = false
    var canPushBomb: Boolean = false

    init {
        when (bType) {
            0 -> speedBoost = 2
            1 -> bombBoost = 1
            2 -> explosionLen = 1
            3 -> canPushBomb = true
            4 -> speedBoost = 2
            5 -> shuffleKeys = true
            6 -> delayBoost = 6000
            7 -> {
                speedBoost = 2
                delayBoost = 6000
            }
            8 -> {
                speedBoost = 2
                delayBoost = 6000
                shuffleKeys = true
            }
        }
    }
}