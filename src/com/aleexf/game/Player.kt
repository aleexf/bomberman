package com.aleexf.game

import com.aleexf.game.world.GameWorld
import com.aleexf.game.world.cell.Bomb
import com.aleexf.game.world.cell.Object


class Player(x:Int, y:Int, val name:String, val playerId:Int, val world:GameWorld):
        Object(x, y, true, 2) {
    var speed = 10
    var alive:Boolean = false
    var bombDelay:Long = 3000
    fun placeBomb():Bomb = Bomb(x, y, bombDelay)
    fun move(dir:Direction) {
        val px = this.x + dir.dx * speed
        val py = this.y + dir.dy * speed
        if (px < 0 || px == world.rows) return
        if (py < 0 || py == world.cols) return
        if (world.anyObject(this.collision, px/32, py/32)) return
        this.x = px
        this.y = py
    }
}