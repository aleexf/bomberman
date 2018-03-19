package com.aleexf.game

import com.aleexf.game.world.GameWorld
import com.aleexf.game.world.cell.Bomb
import com.aleexf.game.world.cell.Object


class Player(x:Int, y:Int, val name:String, val playerId:Int, val world:GameWorld):
        Object(x, y, true, 2) {
    var alive:Boolean = false
    var bombDelay:Long = 3000
    var explosionLen = 2
    var speed = 5
    var animType = 0
    var direction:Direction = Direction.DOWN
    var availableBombs = 1
    fun placeBomb():Bomb = Bomb((x+16)/32, (y+16)/32, bombDelay, this, explosionLen)
    fun move(dir:Direction) {
        val px = this.x + dir.dx * speed
        val py = this.y + dir.dy * speed
        if (px < 0 || px == world.rows) return
        if (py < 0 || py == world.cols) return
        if (world.anyObject(this.collision, (px+16+dir.dx*16)/32, (py+16+dir.dy*16)/32)) return
        this.x = px
        this.y = py
        direction = dir
        animType = (animType+1) % 3
    }
}