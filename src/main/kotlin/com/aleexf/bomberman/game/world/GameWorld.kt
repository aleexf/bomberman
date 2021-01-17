package com.aleexf.bomberman.game.world

import com.aleexf.bomberman.game.world.objects.GameObject

class GameWorld {
    var rows: Int = 15
    var cols: Int = 15
    var objects: MutableList<GameObject> = mutableListOf()

    fun objectById(objectId: Long): GameObject
            = objects.first { it.objectId == objectId }
}
