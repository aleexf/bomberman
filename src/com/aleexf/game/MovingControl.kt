package com.aleexf.game

import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent

import com.aleexf.game.drawer.WorldDrawer

class MovingControl(val player:Player, val drawer:WorldDrawer):Thread() {
    val keyboard:KeyboardControl = KeyboardControl()
    init {
        drawer.frame.addKeyListener(keyboard)
        isDaemon = true
    }
    override fun run() {
        while (true) {
            sleep(300)
        }
    }
}

class KeyboardControl:KeyAdapter() {
    val x:Int = 0
    val keysPressed:ArrayList<Boolean> = ArrayList<Boolean>(255)
    init {
        println(keysPressed.size)
    }
    override fun keyPressed(e: KeyEvent?) {
        if (e != null) {
            keysPressed[e.keyCode] = true
        }
    }

    override fun keyReleased(e: KeyEvent?) {
        if (e != null) {
            keysPressed[e.keyCode] = false
        }
    }
}