package com.aleexf.game.world

import com.aleexf.game.Player
import com.aleexf.game.world.cell.*
import com.aleexf.game.sound.Sounds
import com.aleexf.game.sound.Player.playSound

class Detonator(val world: GameWorld, val player: Player): Thread() {
    override fun run() {
        if (player.availableBombs == 0) return
        if (world.anyObject(2, player.x/32, player.y/32)) return
        val bomb:Bomb = player.placeBomb()
        bomb.owner.availableBombs--
        world.objects.add(bomb)
        sleep(bomb.delay)
        if (!world.objects.contains(bomb)) return
        detonate(bomb)
    }
    private fun doExplosion(px:Int, py:Int): Boolean {
        for (block in world.objects) {
            if (block.x == px && block.y == py && !block.breakByExplosion) {
                return true
            }
        }
        world.explosion.add(Explosion(px, py))
        for (player in world.players) {
            if (player.x/32 == px && player.y/32 == py) {
                player.alive = false
            }
        }
        world.objects.filter {it.x == px && it.y == py}.forEach {
            if (it is Bomb) detonate(it)
            else if (it.breakByExplosion) {
                world.objects.remove(it)
                return@doExplosion true
            }
        }
        return false
    }
    private fun detonate(bomb: Bomb) {
        if (!world.objects.contains(bomb)) return
        if (!world.onServer) playSound(Sounds.EXPLOSION)
        world.objects.remove(bomb)
        bomb.owner.availableBombs++
        var px = bomb.x
        var py = bomb.y
        doExplosion(px, py)
        for (i in 1..bomb.explosionLen) {
            --px
            if (doExplosion(px, py)) break
        }
        px = bomb.x
        py = bomb.y
        for (i in 1..bomb.explosionLen) {
            ++px
            if (doExplosion(px, py)) break
        }
        px = bomb.x
        py = bomb.y
        for (i in 1..bomb.explosionLen) {
            ++py
            if (doExplosion(px, py)) break
        }
        px = bomb.x
        py = bomb.y
        for (i in 1..bomb.explosionLen) {
            --py
            if (doExplosion(px, py)) break
        }

    }
}