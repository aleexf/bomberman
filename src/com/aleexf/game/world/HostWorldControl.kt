package com.aleexf.game.world

import java.util.Random

import com.aleexf.game.world.cell.Box
import com.aleexf.game.world.cell.Bomb
import com.aleexf.game.world.cell.Bonus
import com.aleexf.game.world.cell.Object

class HostWorldControl(val gameWorld: GameWorld, val sender:(msg:String) -> Unit): Thread() {
    private val BONUS_COUNT = 15
    private val rnd = Random()
    private val bonuses: MutableList<Bonus> = mutableListOf()
    init {
        generateBonuses()
        this.start()
    }
    override fun run() {
        while (true) {
            bonuses.removeIf {
                if (!gameWorld.anyObject(1, it.x, it.y)) {
                    sender("action appeared_bonus ${it.x} ${it.y} ${it.bType}")
                    true
                } else {
                    false
                }
            }
            val placedBonuses = gameWorld.objects.filter {it is Bonus}
            for (player in gameWorld.players) {
                val bonus: Bonus? = placedBonuses.firstOrNull {
                    it.x == player.x / 32 && it.y == player.y / 32
                } as Bonus?
                if (bonus != null) {
                    sender("action picked_bonus ${player.playerId} ${bonus.x} ${bonus.y}")
                }
            }
            gameWorld.objects.filter { it is Bomb && it.collision == 2 }.forEach { bomb: Object -> Unit
                if (gameWorld.players.none {it.alive && it.x/32 == bomb.x && it.y/32 == bomb.y}) {
                    sender("action change_collision ${bomb.x} ${bomb.y}")
                }
            }
            if (!gameWorld.players.isEmpty() && (gameWorld.players.count{ it.alive } <= 1 && gameWorld.players.size > 1
                    || gameWorld.players.count{ it.alive } == 0)) {
                sender("game reload_map ${gameWorld.worldId}")
                sleep(500)
                sender("game start")
            }
            sleep(100)
        }
    }
    fun generateBonuses() {
        bonuses.clear()
        val boxes:MutableList<Object> = gameWorld.objects.filter{ it is Box }.toMutableList()
        for (i in 1..BONUS_COUNT) {
            if (boxes.isEmpty()) break
            val bType = rnd.nextInt(4)
            val cIndex = rnd.nextInt(boxes.size)
            bonuses.add(Bonus(boxes[cIndex].x, boxes[cIndex].y, bType))
            boxes.removeAt(cIndex)
        }
    }
}