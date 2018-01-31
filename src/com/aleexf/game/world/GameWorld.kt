package com.aleexf.game.world

import com.aleexf.game.Player
import com.aleexf.game.StaticPlayer
import com.aleexf.game.world.cell.*
import com.aleexf.net.client.Connection

class GameWorld(val nick:String, val ip:String) {
    val rows:Int
    val cols:Int
    val localPlayer:Player
    val connection:Connection
    val blocks: Array<Block> = arrayOf()
    val boxes: MutableSet<Box> = mutableSetOf()
    val usedGrid: MutableSet<List<Int>> = mutableSetOf()
    val players: MutableSet<StaticPlayer> = mutableSetOf()
    init {
        rows = 15 * 3
        cols = 15 * 3
        connection = Connection(nick, ip)
        localPlayer = Player(0, 0, nick, this)
        connection.sendMessage("get_world_id")
        loadWorld(connection.getInt())
        val worldUpdater:WorldUpdater = WorldUpdater(this)
        worldUpdater.start()
    }
    fun anyObject(collision: Int, x: Int, y: Int): Boolean = usedGrid.contains(listOf(x, y))
    fun loadWorld(worldId:Int) {

    }
}


class WorldUpdater(val world:GameWorld):Thread() {
    override fun run() {
        while (true) {

        }
    }
}
