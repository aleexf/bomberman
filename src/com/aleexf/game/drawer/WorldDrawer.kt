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



class WorldDrawerTool(var world:GameWorld) :JPanel() {
    val iBlock: Image
    val iBox: Image
    val iPlayer:Image
    init {
        iBlock = ImageIO.read(File(".\\data\\texture\\block.png"))
        iBox = ImageIO.read(File(".\\data\\texture\\box.png"))
        iPlayer = ImageIO.read(File(".\\data\\texture\\player\\1\\test.png"))
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


class WorldDrawer(var world: GameWorld):Thread() {
    val frame:JFrame
    val delay:Long
    init {
        isDaemon = true
        frame = JFrame("Bomberman")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.setSize(800, 600);
        frame.isResizable = false
        delay = 1000 / 60

    }
    override fun run() {
        val painter:WorldDrawerTool = WorldDrawerTool(world)
        frame.add(painter)
        frame.isVisible = true
        while (true) {
            sleep(delay);
            painter.repaint()
        }
    }
}