package com.aleexf.game.drawer

import java.io.File
import java.awt.Graphics
import java.awt.Image
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame
import javax.swing.JPanel
import javax.imageio.ImageIO

import com.aleexf.game.MovingControl
import com.aleexf.game.world.GameWorld
import com.aleexf.logging.logger


class WorldDrawerTool(var world:GameWorld) :JPanel() {
    val iBlock: Image
    val iBox: Image
    val iPlayer:Image
    init {
        iBlock = ImageIO.read(File(".\\data\\texture\\block.png"))
        iBox = ImageIO.read(File(".\\data\\texture\\box.png"))
        iPlayer = ImageIO.read(File(".\\data\\texture\\player\\1\\test.png"))
        this.isFocusable = true
    }
    override fun paint(graph:Graphics) {
        /* Blocks */
        for (block in world.blocks) {
            graph.drawImage(iBlock,
                    block.y*32, block.x*32,
                    block.y*32+32, block.x*32+32,
                    0, 0,
                    64, 64,
                    null)
        }
        for (box in world.boxes) {
            graph.drawImage(iBox,
                        box.y*32, box.x*32,
                        box.y*32+32, box.x*32+32,
                        0, 0,
                        64, 64,
                        null)
        }
    }
}


class WorldDrawer(var world: GameWorld, val localID:Int):Thread() {
    private val frame:JFrame
    private val delay:Long
    private val painter:WorldDrawerTool
    private val movingControl:MovingControl
    init {
        painter = WorldDrawerTool(world)
        movingControl = MovingControl(world.connection, world.findPlayerById(localID))
        isDaemon = true
        frame = JFrame("Bomberman")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.setSize(800, 600);
        frame.isResizable = false
        delay = 1000 / 60
    }
    override fun run() {
        painter.addKeyListener(movingControl.keyboard)
        frame.add(painter)
        frame.isVisible = true
        movingControl.start()
        logger.info("[Drawer]: Starting painting")
        while (true) {
            sleep(delay);
            painter.repaint()
        }
    }
}