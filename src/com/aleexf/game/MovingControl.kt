package com.aleexf.game

import java.awt.event.KeyEvent

import com.aleexf.net.client.Connection

class MovingControl(private val connection: Connection,
                    private val player: Player
) : Thread() {
    init {
       isDaemon = true
    }
    override fun run() {
        while (true) {
            sleep(50)
            if (!player.alive) continue
            if (!player.shuffledKeyboard) when {
                KeyboardListener.isKeyPressed(KeyEvent.VK_UP)
                    -> connection.sendMessage("action move ${player.playerId} UP")

                KeyboardListener.isKeyPressed(KeyEvent.VK_DOWN)
                    -> connection.sendMessage("action move ${player.playerId} DOWN")

                KeyboardListener.isKeyPressed(KeyEvent.VK_LEFT)
                    -> connection.sendMessage("action move ${player.playerId} LEFT")

                KeyboardListener.isKeyPressed(KeyEvent.VK_RIGHT)
                    -> connection.sendMessage("action move ${player.playerId} RIGHT")
            } else when {
                KeyboardListener.isKeyPressed(KeyEvent.VK_UP)
                    -> connection.sendMessage("action move ${player.playerId} DOWN")

                KeyboardListener.isKeyPressed(KeyEvent.VK_DOWN)
                    -> connection.sendMessage("action move ${player.playerId} RIGHT")

                KeyboardListener.isKeyPressed(KeyEvent.VK_LEFT)
                    -> connection.sendMessage("action move ${player.playerId} UP")

                KeyboardListener.isKeyPressed(KeyEvent.VK_RIGHT)
                    -> connection.sendMessage("action move ${player.playerId} LEFT")
            }
            if (KeyboardListener.isKeyPressed(KeyEvent.VK_SPACE)) {
                connection.sendMessage("action place_bomb ${player.playerId}")
            }
        }
    }
}