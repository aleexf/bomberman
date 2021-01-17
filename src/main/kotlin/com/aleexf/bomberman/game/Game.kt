package com.aleexf.bomberman.game

import java.util.concurrent.BlockingQueue

import com.aleexf.bomberman.config.ServerConfig
import com.aleexf.bomberman.game.events.Event
import com.aleexf.bomberman.game.world.GameWorld


class Game(private val eventQueue: BlockingQueue<Event>) : Thread() {
    val world: GameWorld = GameWorld()
    private val eventHandler: EventHandler = EventHandler(world)

    private fun updateGameWorld() {
        if (eventQueue.isNotEmpty()) {
            val eventsToDispatch = mutableListOf<Event>()
            eventQueue.drainTo(eventsToDispatch)
            eventsToDispatch.sortedBy { it.timestamp }.forEach { eventHandler.handle(it) }
        }
    }

    private fun tick() {

    }

    override fun run() {
        while (true) {
            updateGameWorld()
            sleep(1000L / ServerConfig.getInstance().tickRate)
        }
    }
}
