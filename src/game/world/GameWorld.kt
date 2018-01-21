package game.world

import game.Player
import game.world.cell.*


class GameWorld {
    val rows:Int
    val cols:Int
    val blocks: Array<Block>
    var boxes: MutableSet<Box>
    init {
        rows = 15 * 3
        cols = 15 * 3
        blocks = arrayOf(Block(0, 0))
        boxes = mutableSetOf(Box(3, 3))
    }
    
}
