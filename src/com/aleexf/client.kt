import com.aleexf.GameServer
import com.aleexf.logging.Logger
import com.aleexf.game.world.GameWorld
import com.aleexf.game.drawer.WorldDrawer
import com.aleexf.game.world.ObjectMover
import com.aleexf.game.world.WorldSync
import com.aleexf.game.sound.Sounds
import com.aleexf.game.sound.Player
import com.aleexf.net.client.Connection
import com.aleexf.gui.MainMenu

fun main(args:Array<String>) {
    val menu = MainMenu()
    menu.join()
    Logger.info("[GUI]: Ip address: ${menu.IpAddress.toString()}")
    Logger.info("[GUI]: Nick: ${menu.nick.toString()}")
    menu.IpAddress = menu.IpAddress?.split('.')?.map{it.toInt().toString()}?.joinToString(separator = ".")

    if (menu.IpAddress == "127.0.0.1") {
        try {
            val server = GameServer(1)
            server.start()
        } catch (e: Exception) {
            Logger.error(msg = "[Server]: Server falls down", silent = false)
            Logger.error("[Exception]", e.toString())
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
            Logger.error("[Exception]", e.toString())
            throw e
        }
        Logger.info("[Client]: Connected. Nick: ${menu.nick.toString()}")

        val gameWorld = GameWorld(menu.nick!!)
        connection.sendMessage("get_world_id")
        gameWorld.worldId = connection.getMessage().toInt()
        gameWorld.loadWorld(gameWorld.worldId)
        val worldSynchronizer = WorldSync(gameWorld, connection, null)
        val objectMover = ObjectMover(gameWorld)
        worldSynchronizer.start()
        objectMover.start()

        Player.playSound(Sounds.BACKGROUND1.randomBackground(), loop = true)

        connection.sendMessage("game init")
        Thread.sleep(100)
        val drawer = WorldDrawer(gameWorld, connection.localId, connection)
        drawer.start()

    } catch(e:Exception) {
        Logger.error("Client fatal error",
                "Look at logs to get more information",
                false)
        Logger.error("[Exception]", e.toString())

    }
}
