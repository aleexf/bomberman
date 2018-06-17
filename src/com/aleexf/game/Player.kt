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
    var canPushBomb = false
    var shuffledKeyboard = false
    fun placeBomb():Bomb = Bomb(x/32, y/32, bombDelay, this, explosionLen)
    fun move(dir:Direction) {
        val px = this.x + dir.dx * 5
        val py = this.y + dir.dy * 5
        if (px < 0 || px == world.rows) return
        if (py < 0 || py == world.cols) return
        if (canPushBomb) {
            val bx = x/32 + dir.dx
            val by = y/32 + dir.dy
            world.objects
                    .filter { it is Bomb && it.collision == 3 && it.x == bx && it.y == by }
                    .forEach {
                        it as Bomb
                        it.speedX = dir.dx
                        it.speedY = dir.dy
                    }
        }
        if (world.anyObject(this.collision+1, (px+dir.dx*16)/32, (py+dir.dy*16)/32)) return
        this.x = this.x + dir.dx * speed
        this.y = this.y + dir.dy * speed
        direction = dir
        animType = (animType+1) % 3
    }
    fun defaultParams() {
        alive = false
        bombDelay = 3000
        explosionLen = 2
        speed = 5
        animType = 0
        direction = Direction.DOWN
        availableBombs = 1
        canPushBomb = false
        shuffledKeyboard = false
    }
    fun applyBonus(b:Bonus) {
        if (b.bType > 3) {
            val oldSpeed = speed
            speed = if (b.speedBoost != 0) b.speedBoost else speed
            bombDelay = if (b.delayBoost != 0) b.delayBoost.toLong() else bombDelay
            shuffledKeyboard = shuffledKeyboard || b.shuffleKeys
            object : Thread() {
                init {
                    isDaemon = true
                    this.start()
                }
                override fun run() {
                    sleep(15 * 1000)
                    speed = oldSpeed
                    bombDelay = 3000
                    shuffledKeyboard = false
                }
            }
        } else {
            speed = Math.min(10, speed + b.speedBoost)
            explosionLen = Math.min(16, explosionLen + b.explosionLen)
            availableBombs += b.bombBoost
            canPushBomb = canPushBomb || b.canPushBomb
        }
    }
}