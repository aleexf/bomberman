package com.aleexf.game.world

import com.aleexf.game.Player
import com.aleexf.game.world.cell.Bomb

class Detonator(val world:GameWorld, val player:Player):Thread() {
    override fun run() {
        val bomb:Bomb = player.placeBomb()
        world.bombs.add(bomb)
        sleep(bomb.delay)
        if (!world.bombs.contains(bomb)) return
        detonate(bomb)
    }
    fun detonate(bomb:Bomb) {
        if (!world.bombs.contains(bomb)) return
        world.bombs.remove(bomb)
        // will be added soon
    }
}