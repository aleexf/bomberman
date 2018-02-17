package com.aleexf.game.world.cell

import com.aleexf.game.Player

class Bomb(x:Int, y:Int, val delay:Long, val owner:Player, val explosionLen: Int):
        Object(x, y, true, 3)