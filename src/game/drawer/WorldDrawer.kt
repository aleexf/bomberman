package game.drawer

import java.awt.Graphics
import javax.swing.JFrame
import javax.swing.JPanel
import game.world.GameWorld

class WorldDrawerTool(val world:GameWorld) :JPanel() {
    override fun paint(graph:Graphics) {

    }
}


class WorldDrawer(val world: GameWorld):Thread() {
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
        frame.isVisible = true
        frame.add(painter)
        while (true) {
            sleep(delay);
            painter.repaint()
        }
    }
}