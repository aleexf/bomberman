package com.aleexf.game.world

import java.io.File

import com.aleexf.game.world.cell.Box
import com.aleexf.game.world.cell.Block
import com.aleexf.game.world.cell.HeavyBox
import com.aleexf.game.world.cell.Teleport
import com.aleexf.logging.LoggingFactories
import com.aleexf.utils.xml.XMLObject

class WorldLoader(private val world: GameWorld) {
    private val logger by lazy {
        when (world.onServer) {
            true -> LoggingFactories.serverFactory
            false -> LoggingFactories.clientFactory
        }.getLogger(this.javaClass.name)
    }

    fun loadWorld(worldId: Int) {
        val path = "./data/levels/level-$worldId.xml"
        val dataFile: File = try {
            File(path)
        } catch (e: NoSuchFileException) {
            logger.error("Failed to open $path file. File doesn't exists")
            logger.error(e.toString())
            throw e
        }
        val data = XMLObject(dataFile)["Level"]
        world.objects.clear()
        world.explosion.clear()

        try {
            var x = XMLObject.contentToInt(data["Map"]["HeavyBoxes"].getAllEntries("x")).requireNoNulls()
            var y = XMLObject.contentToInt(data["Map"]["HeavyBoxes"].getAllEntries("y")).requireNoNulls()
            for (i in 0 until x.size) {
                world.objects.add(HeavyBox(x[i], y[i]))
            }
            x = XMLObject.contentToInt(data["Map"]["Boxes"].getAllEntries("x")).requireNoNulls()
            y = XMLObject.contentToInt(data["Map"]["Boxes"].getAllEntries("y")).requireNoNulls()
            for (i in 0 until x.size) {
                world.objects.add(Box(x[i], y[i]))
            }
            x = XMLObject.contentToInt(data["Map"]["Blocks"].getAllEntries("x")).requireNoNulls()
            y = XMLObject.contentToInt(data["Map"]["Blocks"].getAllEntries("y")).requireNoNulls()
            for (i in 0 until x.size) {
                world.objects.add(Block(x[i], y[i]))
            }
            x = XMLObject.contentToInt(data["Map"]["PlayerSpawn"].getAllEntries("x")).requireNoNulls()
            y = XMLObject.contentToInt(data["Map"]["PlayerSpawn"].getAllEntries("y")).requireNoNulls()
            for (i in 0 until x.size) {
                world.spawnCrd[i] = listOf(x[i] * 32 + 16, y[i] * 32 + 16)
            }
            for (i in 1..3) {
                if (data["Map"]["Teleport"].containsTag("tp-$i")) {
                    val x1 = XMLObject.contentToInt(data["Map"]["Teleport"]["tp-$i"]["x1"])!!
                    val y1 = XMLObject.contentToInt(data["Map"]["Teleport"]["tp-$i"]["y1"])!!
                    val x2 = XMLObject.contentToInt(data["Map"]["Teleport"]["tp-$i"]["x1"])!!
                    val y2 = XMLObject.contentToInt(data["Map"]["Teleport"]["tp-$i"]["y1"])!!
                    val tp1 = Teleport(x1, y1, i - 1)
                    val tp2 = Teleport(x2, y2, i - 1, tp1)
                    tp1.other = tp2
                    world.objects.add(tp1)
                    world.objects.add(tp2)
                }
            }

        } catch (e: Exception) {
            logger.error("Failed to load level-$worldId.xml\nSee logs to get more information")
            logger.warning(e.toString())
        }
    }
}