package com.aleexf.game.world

import com.aleexf.game.world.GameWorld
import com.aleexf.game.world.cell.Explosion

class Animator(val world:GameWorld):Thread() {
    init {
        isDaemon = true
    }
    override fun run() {
        while (true) {
            sleep(1000L/30)
            val toRemove :MutableList<Explosion> = mutableListOf()
            for (explosion in world.explosion) {
                if (explosion.animType == 30) toRemove.add(explosion)
                else explosion.animType++
            }
            for (explosion in toRemove) {
                if (world.explosion.contains(explosion)) {
                    world.explosion.remove(explosion)
                }
            }
        }
    }
}