package game

import java.util.Random

import game.world.cell.Bomb
import game.world.cell.Object

class MovingControl:Thread() {
    override fun run() {

    }
}

class Player(x:Int, y:Int, val name:String): Object(x, y, true, 2) {
    init {
        val id:Int = Random().nextInt(0, 100000);
    }
    var bombDelay:Int = 3000;
    fun placeBomb():Bomb = Bomb(x, y, bombDelay);
    
}