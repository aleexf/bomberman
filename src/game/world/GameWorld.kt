package game.world

import game.Player
import game.world.cell.*


class GameWorld {
    val rows:Int
    val cols:Int
    val usedGrid: MutableSet<List<Int>>
    val blocks: Array<Block>
    var boxes: MutableSet<Box>
    var players: MutableSet<Player>
    init {
        rows = 15 * 3
        cols = 15 * 3
        blocks = arrayOf(Block(0, 0))
        boxes = mutableSetOf(Box(1, 1))
        players = mutableSetOf(Player(2, 2, "aleex", this))
        usedGrid = mutableSetOf()
    }
    fun anyObject(collision: Int, x: Int, y: Int): Boolean = usedGrid.contains(listOf(x, y))
}
