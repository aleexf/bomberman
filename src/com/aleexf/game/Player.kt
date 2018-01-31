package com.aleexf.game

import com.aleexf.game.Direction
import com.aleexf.game.world.GameWorld
import com.aleexf.game.world.cell.Bomb
import com.aleexf.game.world.cell.Object
import java.util.Random
import kotlin.system.exitProcess

class MovingControl(val player:Player):Thread() {
    override fun run() {
        while (true) {
            if (!player.alive) sleep(100)

        }
    }
}

class Player(x:Int, y:Int, val name:String, val world:GameWorld): Object(x, y, true, 2) {
    val movesChecker:MovingControl
    val playerId:Int
    var alive:Boolean
    init {
        world.connection.sendMessage("get_player_id")
        playerId = world.connection.getInt()
        if (playerId == -1) {
            exitProcess(0);
        }
        alive = true
        movesChecker = MovingControl(this)
    }
    var bombDelay:Int = 3000
    fun placeBomb():Bomb = Bomb(x, y, bombDelay)
    fun move(dir:Direction) {
        val px = this.x + dir.dx
        val py = this.y + dir.dy
        if (px < 0 || px == world.rows) return
        if (py < 0 || py == world.cols) return
        if (world.anyObject(this.collision, px+dir.dx, py+dir.dy)) return
        this.x = px
        this.y = py
    }
}