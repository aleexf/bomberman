package com.aleexf.graphics.drawer

import java.awt.Image
import java.io.File
import javax.imageio.ImageIO
import java.lang.System.exit

import com.aleexf.logging.Logger
import com.aleexf.logging.LoggingFactories
import java.awt.image.ImagingOpException
import java.lang.Exception

object TextureManager {
    val iBlock: Image
    val iBox: Image
    val iFloor: Image
    val iBomb: Image
    val iToxicBomb: Image
    val iHeavyBox: Image
    val iPlayer: List<List<Image>>
    val iExplosion: List<Image>
    val iBonus: List<Image>
    val iTeleport: List<Image>

    private val logger by lazy { LoggingFactories.clientFactory.getLogger(this.javaClass.name) }

    init {
        iBlock = loadTexture("./data/texture/block.png")
        iBox = loadTexture("./data/texture/box.png")
        iFloor = loadTexture("./data/texture/floor.png")
        iBomb = loadTexture("./data/texture/bomb.png")
        iToxicBomb = loadTexture("./data/texture/toxic_bomb.png")
        iHeavyBox = loadTexture("./data/texture/heavy_box.png")
        iBonus = listOf(
                loadTexture("./data/texture/bonus/speed.png"),
                loadTexture("./data/texture/bonus/bomb.png"),
                loadTexture("./data/texture/bonus/explosion.png"),
                loadTexture("./data/texture/bonus/bombpush.png"),
                loadTexture("./data/texture/bonus/skull.png")
                )
        iPlayer = List(4) {playerId: Int ->
            List(12) {
                var path = "./data/texture/player/${playerId+1}/"
                if (it < 3) path += "up"
                else if (it < 6) path += "down"
                else if (it < 9) path += "left"
                else path += "right"
                loadTexture(path+"_0"+(1+it%3).toString()+".png")
            }
        }
        iExplosion = List(32) {
            loadTexture("./data/texture/explosion/expl-${if (it < 10) "0"+it.toString() else it.toString()}.png")
        }
        iTeleport = List(3) {
                loadTexture("./data/texture/teleport/0$it.png")
        }
    }
    private fun loadTexture(path:String): Image {
        try {
            return ImageIO.read(File(path))
        } catch (e: NoSuchFileException) {
            logger.error("Texture at $path is missing")
            throw e
        } catch (e: Exception) {
            logger.error("Parsing of $path failed")
            throw e
        }
    }
}