import com.aleexf.logging.Logger
import com.aleexf.game.world.GameWorld
import com.aleexf.game.drawer.WorldDrawer
import com.aleexf.net.client.Connection
import com.aleexf.net.server.Server
import com.aleexf.gui.MainMenu


fun main(args:Array<String>) {
    val menu = MainMenu()
    menu.join()
    Logger.info("[GUI]: Ip address: ${menu.IpAddress.toString()}")
    Logger.info("[GUI]: Nick: ${menu.nick.toString()}")
    menu.IpAddress = menu.IpAddress?.split('.')?.map{it.toInt().toString()}?.joinToString(separator = ".")

    if (menu.IpAddress == "127.0.0.1") {
        try {
            val server = Server(1)
            server.start()
        } catch (e: Exception) {
            Logger.error(msg = "[Server]: Server falls down", silent = false)
        }
    }

    Thread.sleep(1000)

    try {
        Logger.info("[Client]: Connecting to ${menu.IpAddress.toString()}...")
        var connection:Connection? = null
        try {
            connection = Connection(menu.nick!!, menu.IpAddress!!)
        } catch (e:Exception) {
            Logger.error(msg = "[Client]: Connection failed", silent = false)
            throw e
        }
        Logger.info("[Client]: Connected. Nick: ${menu.nick.toString()}")

        val gameWorld = GameWorld(menu.nick!!, connection)
        connection.sendMessage("game init")
        Thread.sleep(100)
        val drawer = WorldDrawer(gameWorld, connection.localId)
        drawer.start()
        connection.sendMessage("game start")

    } catch(e:Exception) {
        Logger.error("Client fatal error",
                "Look at logs to get more information",
                false)

    }
}
