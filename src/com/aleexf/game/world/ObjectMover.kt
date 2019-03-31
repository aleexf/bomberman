package com.aleexf.game.world

import com.aleexf.game.world.cell.Bomb

class ObjectMover(val world:GameWorld): Thread() {
    init {
        isDaemon = true
    }
    override fun run() {
        while (true) {
            sleep(50)
            for (bomb in world.objects) {
                if (bomb !is Bomb) continue
                if (bomb.speedX == 0 && bomb.speedY == 0) continue
                val px = bomb.realX + bomb.speedX * bomb.BOMBSPEED
                val py = bomb.realY + bomb.speedY * bomb.BOMBSPEED
                if ((px+11*bomb.speedX)/32 == bomb.x && (py+11*bomb.speedY)/32 == bomb.y) {
                    bomb.realX = px
                    bomb.realY = py
                } else {
                    if (world.anyObject(bomb.collision, (px+11*bomb.speedX)/32, (py+11*bomb.speedY)/32)) {
                        bomb.speedX = 0
                        bomb.speedY = 0
                    } else {
                        bomb.realX = px
                        bomb.realY = py
                        bomb.x = px/32
                        bomb.y = py/32
                    }
                }
            }
        }
    }
}