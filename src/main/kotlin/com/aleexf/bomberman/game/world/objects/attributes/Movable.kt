package com.aleexf.bomberman.game.world.objects.attributes

import com.aleexf.bomberman.game.Direction
import com.aleexf.bomberman.game.world.objects.GameObject

class Movable(owner: GameObject, var speed: Int) : Attribute(owner, "Movable") {
    var isMoving: Boolean = false
    var direction: Direction = Direction.UP

    fun moveForward() {
        isMoving = true
        owner.x += speed * direction.dx
        owner.y += speed * direction.dy
    }
}
