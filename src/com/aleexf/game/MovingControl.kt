package com.aleexf.game

import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent

import com.aleexf.net.client.Connection

class MovingControl(val connection:Connection, val player:Player):Thread() {
    init {
       isDaemon = true
    }
    override fun run() {
        while (true) {
            sleep(50)
            if (!player.alive) continue
            if (KeyboardControl.isKeyPressed(KeyEvent.VK_UP)) {
                connection.sendMessage("action move ${player.playerId} UP")
            } else if (KeyboardControl.isKeyPressed(KeyEvent.VK_DOWN)) {
                connection.sendMessage("action move ${player.playerId} DOWN")
            } else if (KeyboardControl.isKeyPressed(KeyEvent.VK_LEFT)) {
                connection.sendMessage("action move ${player.playerId} LEFT")
            } else if (KeyboardControl.isKeyPressed(KeyEvent.VK_RIGHT)) {
                connection.sendMessage("action move ${player.playerId} RIGHT")
            }
            if (KeyboardControl.isKeyPressed(KeyEvent.VK_SPACE)) {
                connection.sendMessage("action place_bomb ${player.playerId}")
            }
        }
    }
}

object KeyboardControl:KeyAdapter() {
    val keysPressed:MutableSet<Int> = mutableSetOf()
    fun isKeyPressed(keyId:Int) = keysPressed.contains(keyId)
    override fun keyPressed(e: KeyEvent) {
        keysPressed.add(e.keyCode)
    }
    override fun keyReleased(e: KeyEvent) {
        keysPressed.remove(e.keyCode)
    }
}