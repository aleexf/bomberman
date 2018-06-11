package com.aleexf

import com.aleexf.game.world.GameWorld
import com.aleexf.game.world.WorldUpdater
import com.aleexf.game.world.HostWorldControl
import com.aleexf.net.server.Server

class GameServer(worldId:Int): Server(worldId) {
    private val gameWorld = GameWorld("server")
    private val hostWorldControl = HostWorldControl(gameWorld, this::resendMessage)
    private val worldUpdater = WorldUpdater(gameWorld, null, hostWorldControl)
    init {
        gameWorld.worldId = worldId
    }
    override fun resendMessage(msg:String) {
        super.resendMessage(msg)
        worldUpdater.updateWorld(msg)
    }
}