package com.aleexf.graphics.drawer

import javax.swing.JFrame

import com.aleexf.config.Config
import com.aleexf.game.MovingControl
import com.aleexf.game.KeyboardListener
import com.aleexf.game.world.GameWorld
import com.aleexf.graphics.drawer.Animator
import com.aleexf.net.client.Connection


class WorldDrawer(var world: GameWorld, localID: Int, connection: Connection): Thread() {
    private val frame: JFrame
    private val delay: Long
    private val painter: WorldDrawerTool
    private val movingControl: MovingControl
    private val explosionAnimator: Animator
    init {
        painter = WorldDrawerTool(world)
        movingControl = MovingControl(connection, world.findPlayerById(localID))
        explosionAnimator = Animator(world)

        isDaemon = true
        frame = JFrame("Bomberman v" + Config.Version)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.setSize(Config.WindowWidth, Config.WindowHeight);
        frame.isResizable = false
        delay = 1000L / Config.FPS
    }
    override fun run() {
        painter.addKeyListener(KeyboardListener)
        frame.add(painter)
        frame.isVisible = true
        movingControl.start()
        explosionAnimator.start()
        while (true) {
            sleep(delay);
            painter.repaint()
        }
    }
}