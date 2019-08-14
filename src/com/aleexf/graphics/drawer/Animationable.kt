package com.aleexf.graphics.drawer

interface Animationable {
    var animState: Long?
    val animDelay: Long?
    val creationTime: Long?

    fun updateAnimState()
}