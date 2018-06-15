package com.aleexf.game.world.cell

import com.aleexf.game.Player

class Bomb(x:Int, y:Int, val delay:Long, val owner:Player, val explosionLen: Int):
        Object(x, y, true, 2) {
    val BOMBSPEED = 5
    var realX = x*32+16
    var realY = y*32+16
    var speedX = 0
    var speedY = 0
}