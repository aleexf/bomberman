package com.aleexf.game

import com.aleexf.game.world.cell.Object

class StaticPlayer(x:Int, y:Int, val playerId:Int):Object(x, y, true, 2) {
    val animType:Int = 0
}