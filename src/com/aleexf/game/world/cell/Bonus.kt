package com.aleexf.game.world.cell

class Bonus(x:Int, y:Int, val bType:Int): Object(x, y, true, 1) {
    val speedBoost: Int
    val bombBoost: Int
    val explosionLen: Int
    init {
        when (bType) {
            0 -> {
                speedBoost = 2
                bombBoost = 0
                explosionLen = 0
            }
            1 -> {
                speedBoost = 0
                bombBoost = 1
                explosionLen = 0
            }
            2 -> {
                speedBoost = 0
                bombBoost = 0
                explosionLen = 1
            }
            else -> {
                speedBoost = 0
                bombBoost = 0
                explosionLen = 0
            }
        }
    }
}