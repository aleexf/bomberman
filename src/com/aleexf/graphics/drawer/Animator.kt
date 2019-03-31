package com.aleexf.graphics.drawer

import com.aleexf.game.world.GameWorld

class Animator(val world: GameWorld): Thread() {
    init {
        isDaemon = true
    }
    override fun run() {
        while (true) {
            //! each object in corutine
            //! Remove when its removed
        }
    }
}