import java.lang.System.exit

import com.aleexf.logging.logger
import com.aleexf.game.world.GameWorld
import com.aleexf.game.drawer.WorldDrawer
import com.aleexf.net.client.Connection
import com.aleexf.net.server.Server

fun main(args:Array<String>) {
    // gui must be here
    try {
        val localhost = Server(1)
        localhost.start()
    } catch (e:Exception) {
        println("Server falls down")
    }
    try {
        val connection = Connection("aleex", "127.0.0.1")
        val gameWorld = GameWorld("aleex", connection)
        connection.sendMessage("game init")
        val drawer = WorldDrawer(gameWorld, connection.localId)
        drawer.start()
        connection.sendMessage("game start")
    } catch(e:Exception) {
        logger.error("[Client]: Fatal")
    }
}

