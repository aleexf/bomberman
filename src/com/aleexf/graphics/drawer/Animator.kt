package com.aleexf.graphics.drawer

import com.aleexf.game.world.GameWorld

class Animator(val world: GameWorld): Thread() {
    init {
        isDaemon = true
    }

    override fun run() {
        while (true) {
            sleep(50)
            world.objects.forEach {
                if (it is Animationable && it.animDelay != null)
                    it.updateAnimState()
            }
        }
    }
}