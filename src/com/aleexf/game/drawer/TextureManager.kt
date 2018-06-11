package com.aleexf.game.drawer

import java.awt.Image
import java.io.File
import javax.imageio.ImageIO
import java.lang.System.exit

import com.aleexf.logging.Logger

object TextureManager {
    val iBlock: Image
    val iBox: Image
    val iFloor: Image
    val iBomb: Image
    val iToxicBomb: Image
    val iPlayer: List<List<Image>>
    val iExplosion: List<Image>
    val iBonus: List<Image>
    init {
        iBlock = loadTexture("./data/texture/block.png")
        iBox = loadTexture("./data/texture/box.png")
        iFloor = loadTexture("./data/texture/floor.png")
        iBomb = loadTexture("./data/texture/bomb.png")
        iToxicBomb = loadTexture("./data/texture/toxic_bomb.png")
        iBonus = listOf(
                loadTexture("./data/texture/bonus/speed.png"),
                loadTexture("./data/texture/bonus/explosion.png"),
                loadTexture("./data/texture/bonus/bomb.png")
                )
        iPlayer = List(4) {playerId:Int ->
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
    }
    private fun loadTexture(path:String):Image {
        try {
            return ImageIO.read(File(path))
        } catch (e:Exception) {
            Logger.error("Texture missing", "File $path is missing", false)
            exit(0)
            throw e
        }
    }
}