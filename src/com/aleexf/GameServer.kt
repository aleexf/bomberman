package com.aleexf

import com.aleexf.game.world.GameWorld
import com.aleexf.game.world.ObjectMover
import com.aleexf.game.world.WorldSync
import com.aleexf.game.world.HostWorldControl
import com.aleexf.net.server.Server

class GameServer(worldId:Int): Server(worldId) {
    private val gameWorld = GameWorld("server")
    private val hostWorldControl = HostWorldControl(gameWorld, this::resendMessage)
    private val worldSynchronizer = WorldSync(gameWorld, null, hostWorldControl)
    private val objectMover = ObjectMover(gameWorld)
    init {
        gameWorld.worldId = worldId
        objectMover.start()
    }
    override fun resendMessage(msg:String) {
        super.resendMessage(msg)
        worldSynchronizer.updateWorld(msg)
    }
}