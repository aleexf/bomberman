package com.aleexf.graphics.drawer

interface Animationable {
    var animState: Int?
    val animDelay: Long?

    fun nextAnimState()
}