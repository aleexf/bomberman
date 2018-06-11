package com.aleexf.game

import com.aleexf.game.world.GameWorld
import com.aleexf.game.world.cell.Bomb
import com.aleexf.game.world.cell.Bonus
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
        val px = this.x + dir.dx * 5
        val py = this.y + dir.dy * 5
        if (px < 0 || px == world.rows) return
        if (py < 0 || py == world.cols) return
        if (world.anyObject(this.collision, (px+16+dir.dx*16)/32, (py+16+dir.dy*16)/32)) return
        world.usedGrid.remove(Pair(this.collision+1, Pair(this.x, this.y)))
        this.x = this.x + dir.dx * speed
        this.y = this.y + dir.dy * speed
        direction = dir
        animType = (animType+1) % 3
        world.usedGrid.add(Pair(this.collision, Pair(this.x, this.y)))
    }
    fun defaultParams() {
        alive = false
        bombDelay = 3000
        explosionLen = 2
        speed = 5
        animType = 0
        direction = Direction.DOWN
        availableBombs = 1
    }
    fun applyBonus(b:Bonus) {
        speed = Math.min(10, speed+b.speedBoost)
        explosionLen = Math.min(16, explosionLen+b.explosionLen)
        availableBombs += b.bombBoost
    }
}