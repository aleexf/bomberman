package com.aleexf

import com.aleexf.game.world.GameWorld
import com.aleexf.game.world.WorldUpdater
import com.aleexf.net.server.Server

class GameServer(worldId:Int): Server(worldId) {
    private val gameWorld = GameWorld("server")
    private val worldUpdater = WorldUpdater(gameWorld, null)
    init {
        gameWorld.worldId = worldId
    }
    override fun resendMessage(msg:String) {
        super.resendMessage(msg)
        worldUpdater.updateWorld(msg)
    }
}