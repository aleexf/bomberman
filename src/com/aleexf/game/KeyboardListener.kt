package com.aleexf.game

import java.awt.event.KeyEvent
import java.awt.event.KeyAdapter


object KeyboardListener : KeyAdapter() {
    private val keysPressed: MutableSet<Int> = mutableSetOf()

    fun isKeyPressed(keyId: Int) = keysPressed.contains(keyId)

    override fun keyPressed(e: KeyEvent) {
        keysPressed.add(e.keyCode)
    }
    override fun keyReleased(e: KeyEvent) {
        keysPressed.remove(e.keyCode)
    }
}