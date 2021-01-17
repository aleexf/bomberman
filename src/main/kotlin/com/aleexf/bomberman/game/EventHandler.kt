package com.aleexf.bomberman.game

import com.aleexf.bomberman.utils.Serializator
import com.aleexf.bomberman.game.events.Event
import com.aleexf.bomberman.game.world.GameWorld
import com.aleexf.bomberman.game.world.objects.attributes.Movable

class EventHandler(private val world: GameWorld) {
    fun handle(event: Event) {
        try {
            when (event.details["type"]) {
                "action" -> {
                    when (event.details["action_type"]) {
                        "move" -> {
                            val obj = world.objectById(event.details["object_id"]!!.toLong())
                            (obj.findAttribute("Movable") as Movable).moveForward()
                        }
                    }
                }
            }
        } catch (exc: RuntimeException) {
            //! TODO: log the error
        }
    }

    private val gson = Serializator.instance


}
