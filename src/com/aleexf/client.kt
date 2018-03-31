import javax.swing.JOptionPane

import com.aleexf.logging.logger
import com.aleexf.game.world.GameWorld
import com.aleexf.game.drawer.WorldDrawer
import com.aleexf.net.client.Connection
import com.aleexf.net.server.Server
import com.aleexf.gui.MainMenu

fun main(args:Array<String>) {
    val menu = MainMenu()
    Thread.sleep(100)
    while (menu.notClosed()) Thread.sleep(30)
    logger.info("[GUI]: Ip address: ${menu.IpAddress.toString()}")
    logger.info("[GUI]: Nick: ${menu.nick.toString()}")
    menu.IpAddress = menu.IpAddress?.split('.')?.map{it.toInt().toString()}?.joinToString(separator = ".")
    if (menu.IpAddress == "127.0.0.1") {
        try {
            val localhost = Server(1)
            localhost.start()
        } catch (e: Exception) {
            logger.error("[Server]: Server falls down")
        }
    }
    Thread.sleep(1000)
    try {
        logger.info("[Client]: Connecting to ${menu.IpAddress.toString()}...")
        val connection = Connection(menu.nick.toString(), menu.IpAddress.toString())
        logger.info("[Client]: Connected. Nick: ${menu.nick.toString()}")
        val gameWorld = GameWorld(menu.nick.toString(), connection)
        connection.sendMessage("game init")
        Thread.sleep(100)
        val drawer = WorldDrawer(gameWorld, connection.localId)
        drawer.start()
        connection.sendMessage("game start")
    } catch(e:Exception) {
        logger.error("[Client]: Fatal")
        JOptionPane.showMessageDialog(null,
                "Client fatal error.\nLook at logs to get more information",
                "Fatal error",
                JOptionPane.ERROR_MESSAGE)
    }
}

