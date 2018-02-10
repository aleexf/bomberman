package com.aleexf.game

import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.JTextField

import com.aleexf.net.client.Connection
import com.aleexf.game.drawer.WorldDrawer

class MovingControl(val connection:Connection, val player:Player):Thread() {
    val keyboard:KeyboardControl = KeyboardControl()
    init {
        isDaemon = true
    }
    override fun run() {
        while (true) {
            sleep(100)
            if (!player.alive) continue
            if (keyboard.isKeyPressed(KeyEvent.VK_UP)) {
                connection.sendMessage("action move ${player.playerId} UP")
            } else if (keyboard.isKeyPressed(KeyEvent.VK_DOWN)) {
                connection.sendMessage("action move ${player.playerId} DOWN")
            } else if (keyboard.isKeyPressed(KeyEvent.VK_LEFT)) {
                connection.sendMessage("action move ${player.playerId} LEFT")
            } else if (keyboard.isKeyPressed(KeyEvent.VK_RIGHT)) {
                connection.sendMessage("action move ${player.playerId} RIGHT")
            }
            if (keyboard.isKeyPressed(KeyEvent.VK_SPACE)) {
                connection.sendMessage("action place_bomb ${player.playerId}")
            }
        }
    }
}

class KeyboardControl:KeyAdapter() {
    val keysPressed:MutableSet<Int> = mutableSetOf()
    fun isKeyPressed(keyId:Int) = keysPressed.contains(keyId)
    override fun keyPressed(e: KeyEvent?) {
        if (e != null) {
            keysPressed.add(e.keyCode)
        }
    }
    override fun keyReleased(e: KeyEvent?) {
        if (e != null) {
            keysPressed.remove(e.keyCode)
        }
    }
}