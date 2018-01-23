package game

import java.util.Random

import game.Direction
import game.world.GameWorld
import game.world.cell.Bomb
import game.world.cell.Object

class MovingControl(val player:Player):Thread() {
    override fun run() {

    }
}

class Player(x:Int, y:Int, val name:String, val world:GameWorld): Object(x, y, true, 2) {
    val movesChecker:MovingControl
    val playerId:Int
    init {
        // temp
        playerId = 0
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