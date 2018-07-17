package com.aleexf.game.world

import java.io.File

import com.aleexf.game.world.cell.Box
import com.aleexf.game.world.cell.Block
import com.aleexf.game.world.cell.HeavyBox
import com.aleexf.logging.Logger
import com.aleexf.utils.xml.XMLObject

fun loadWorld(worldId: Int, world:GameWorld) {
    val path = "./data/levels/level-$worldId.xml"
    var dataFile: File? = null
    try {
        dataFile = File(path)
    } catch (e: NoSuchFileException) {
        Logger.error("Loading of the level failed.",
                "Failed to open ${path} file. File doesn't exists",
                false
        )
        Logger.error("[Exception]: ", e.toString())
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

    } catch(e: Exception) {
        Logger.error("Level data error",
                "Failed to load level-$worldId.xml\nSee logs to get more information",
                false
        )
        Logger.warning("Exception", e.toString(), false)
    }
}