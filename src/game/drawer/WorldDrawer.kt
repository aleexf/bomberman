package game.drawer

import java.io.File
import java.awt.Graphics
import java.awt.Image
import javax.swing.JFrame
import javax.swing.JPanel
import javax.imageio.ImageIO

import game.world.GameWorld

class WorldDrawerTool(var world:GameWorld) :JPanel() {
    val iBlock: Image
    val iBox: Image
    init {
        iBlock = ImageIO.read(File(".\\data\\texture\\block.png"))
        iBox = ImageIO.read(File(".\\data\\texture\\box.png"))

    }
    override fun paint(graph:Graphics) {
        /* Blocks */
        for (i in 0..world.blocks.size-1) {
            graph.drawImage(iBlock,
                    world.blocks[i].x*30,
                    world.blocks[i].y*30,
                    world.blocks[i].x*30+30,
                    world.blocks[i].y*30+30,
                    0, 0,
                    64, 64,
                    null)
        }
        for (box in world.boxes) {
            graph.drawImage(iBox,
                        box.x*30,
                        box.y*30,
                        box.x*30+30,
                        box.y*30+30,
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