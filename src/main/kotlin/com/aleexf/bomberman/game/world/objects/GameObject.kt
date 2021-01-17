package com.aleexf.bomberman.game.world.objects

import com.aleexf.bomberman.game.world.objects.attributes.Attribute

data class GameObject(
    val objectType: String,
    var x: Int,
    var y: Int,
    var collision: Int,
    var isAlive: Boolean = true,
    var attributes: List<Attribute> = listOf(),
    var objectId: Long = objectsCounter++) {

    companion object {
        private var objectsCounter = 0L
    }

    fun findAttribute(attributeName: String): Attribute?
        = attributes.find { it.attributeName == attributeName }
}
