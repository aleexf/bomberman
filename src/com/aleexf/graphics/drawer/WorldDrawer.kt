package com.aleexf.graphics.drawer

import javax.swing.JFrame

import com.aleexf.config.Config
import com.aleexf.game.MovingControl
import com.aleexf.game.KeyboardListener
import com.aleexf.game.world.GameWorld
import com.aleexf.net.client.Connection


class WorldDrawer(var world: GameWorld, localID: Int, connection: Connection): Thread() {
    private val frame: JFrame
    private val delay: Long
    private val painter: WorldDrawerTool = WorldDrawerTool(world)
    private val movingControl: MovingControl = MovingControl(connection, world.findPlayerById(localID))
    private val animator: Animator = Animator(world)
    init {
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

        animator.start()
        movingControl.start()

        while (true) {
            sleep(delay);
            painter.repaint()
        }
    }
}