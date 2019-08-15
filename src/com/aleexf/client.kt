import com.aleexf.GameServer
import com.aleexf.game.world.GameWorld
import com.aleexf.game.world.ObjectMover
import com.aleexf.game.world.WorldSync
import com.aleexf.game.sound.Sound
import com.aleexf.game.sound.SoundPlayer
import com.aleexf.graphics.drawer.WorldDrawer
import com.aleexf.logging.LoggingFactories
import com.aleexf.net.client.Connection


fun main(args:Array<String>) {

    val logger by lazy { LoggingFactories.clientFactory.getLogger("main") }

    try {
        val server = GameServer(1)
        server.start()
    } catch (e: Exception) {
        logger.error("[Server]: Server falls down")
        logger.warning(e.toString())
    }

    Thread.sleep(1000)

    try {
        logger.info("[Client]: Connecting to ${"127.0.0.1"}...")
        var connection:Connection? = null
        try {
            connection = Connection("nickname", "127.0.0.1")
        } catch (e:Exception) {
            logger.error("[Client]: Connection failed")
            logger.warning(e.toString())
            throw e
        }
        logger.info("[Client]: Connected. Nick: nickname")

        val gameWorld = GameWorld(false)
        connection.sendMessage("get_world_id")
        gameWorld.worldId = connection.getMessage().toInt()
        gameWorld.loadWorld(gameWorld.worldId)
        val worldSynchronizer = WorldSync(gameWorld, connection, null)
        val objectMover = ObjectMover(gameWorld)
        worldSynchronizer.start()
        objectMover.start()

        val backgroundMusic = SoundPlayer(Sound.BACKGROUND1.randomBackground(), loop = true)
        backgroundMusic.play()

        connection.sendMessage("game init")
        Thread.sleep(100)
        val drawer = WorldDrawer(gameWorld, connection.localId, connection)
        drawer.start()

        connection.sendMessage("game reload_map 1")
        connection.sendMessage("game start")

    } catch(e: Exception) {
        logger.error("Client fatal error\nLook logs to get more information")
        logger.warning(e.toString())

    }
}
