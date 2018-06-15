package com.aleexf.game.world.cell

class Bonus(x:Int, y:Int, val bType:Int): Object(x, y, true, 1) {
    var speedBoost: Int = 0
    var bombBoost: Int = 0
    var explosionLen: Int = 0
    var canPushBomb: Boolean = false
    init {
        when (bType) {
            0 -> speedBoost = 2
            1 -> bombBoost = 1
            2 -> explosionLen = 1
            3 -> canPushBomb = true
        }
    }
}