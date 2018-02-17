package com.aleexf.game.drawer

import java.io.File
import java.awt.Graphics
import java.awt.Image
import java.awt.Color
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame
import javax.swing.JPanel
import javax.imageio.ImageIO

import com.aleexf.game.MovingControl
import com.aleexf.game.world.GameWorld
import com.aleexf.game.drawer.WorldDrawerTool
import com.aleexf.game.world.ExplosionAnimator


class WorldDrawer(var world: GameWorld, val localID:Int):Thread() {
    private val frame:JFrame
    private val delay:Long
    private val painter:WorldDrawerTool
    private val movingControl:MovingControl
    private val explosionAnimator:ExplosionAnimator
    init {
        painter = WorldDrawerTool(world)
        movingControl = MovingControl(world.connection, world.findPlayerById(localID))
        explosionAnimator = ExplosionAnimator(world)
        isDaemon = true
        frame = JFrame("Bomberman")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.setSize(495, 518);
        frame.isResizable = false
        delay = 1000 / 60
    }
    override fun run() {
        painter.addKeyListener(movingControl.keyboard)
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