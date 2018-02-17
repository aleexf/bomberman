package com.aleexf.game.world

import com.aleexf.game.Player
import com.aleexf.game.world.cell.Bomb
import com.aleexf.game.world.cell.Explosion

class Detonator(val world:GameWorld, val player:Player):Thread() {
    override fun run() {
        if (player.availableBombs == 0) return
        if (world.anyObject(3, (player.x+16)/32, (player.y+16)/32)) return
        val bomb:Bomb = player.placeBomb()
        bomb.owner.availableBombs--
        world.bombs.add(bomb)
        world.usedGrid.add(listOf(bomb.x, bomb.y))
        sleep(bomb.delay)
        if (!world.bombs.contains(bomb)) return
        detonate(bomb)
    }
    fun doExplosion(px:Int, py:Int):Boolean {
        for (block in world.blocks) {
            if (block.x == px && block.y == py) {
                return true
            }
        }
        world.explosion.add(Explosion(px, py))
        for (player in world.players) {
            if ((player.x+16)/32 == px && (player.y+16)/32 == py) {
                player.alive = false
            }
        }
        for (bomb in world.bombs) {
            if (bomb.x == px && bomb.y == py) {
                detonate(bomb)
            }
        }
        for (box in world.boxes) {
            if (box.x == px && box.y == py) {
                world.boxes.remove(box)
                world.usedGrid.remove(listOf(box.x, box.y))
                return true
            }
        }
        return false
    }
    fun detonate(bomb:Bomb) {
        if (!world.bombs.contains(bomb)) return
        world.bombs.remove(bomb)
        world.usedGrid.remove(listOf(bomb.x, bomb.y))
        bomb.owner.availableBombs++
        var px = bomb.x
        var py = bomb.y
        doExplosion(px, py)
        for (i in 0..bomb.explosionLen) {
            --px
            if (doExplosion(px, py)) break
        }
        px = bomb.x
        py = bomb.y
        for (i in 0..bomb.explosionLen) {
            ++px
            if (doExplosion(px, py)) break
        }
        px = bomb.x
        py = bomb.y
        for (i in 0..bomb.explosionLen) {
            ++py
            if (doExplosion(px, py)) break
        }
        px = bomb.x
        py = bomb.y
        for (i in 0..bomb.explosionLen) {
            --py
            if (doExplosion(px, py)) break
        }
    }
}