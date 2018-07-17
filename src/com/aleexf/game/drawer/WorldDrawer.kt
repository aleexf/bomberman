package com.aleexf.game.drawer

import javax.swing.JFrame

import com.aleexf.game.MovingControl
import com.aleexf.game.KeyboardControl
import com.aleexf.game.world.GameWorld
import com.aleexf.game.world.Animator
import com.aleexf.net.client.Connection


class WorldDrawer(var world: GameWorld, val localID:Int, val connection:Connection):Thread() {
    private val frame:JFrame
    private val delay:Long
    private val painter:WorldDrawerTool
    private val movingControl:MovingControl
    private val explosionAnimator:Animator
    init {
        painter = WorldDrawerTool(world)
        movingControl = MovingControl(connection, world.findPlayerById(localID))
        explosionAnimator = Animator(world)

        isDaemon = true
        frame = JFrame("Bomberman")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.setSize(495, 518);
        frame.isResizable = false
        delay = 1000 / 60
    }
    override fun run() {
        painter.addKeyListener(KeyboardControl)
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