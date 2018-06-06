package com.aleexf.game.drawer

import java.awt.Image
import java.io.File
import javax.imageio.ImageIO

object TextureManager {
    val iBlock: Image
    val iBox: Image
    val iFloor: Image
    val iBomb: Image
    val iToxicBomb: Image
    val iPlayer: List<List<Image>>
    val iExplosion: List<Image>
    init {
        iBlock = ImageIO.read(File("./data/texture/block.png"))
        iBox = ImageIO.read(File("./data/texture/box.png"))
        iFloor = ImageIO.read(File("./data/texture/floor.png"))
        iBomb = ImageIO.read(File("./data/texture/bomb.png"))
        iToxicBomb = ImageIO.read(File("./data/texture/toxic_bomb.png"))
        iPlayer = listOf(
                listOf(
                        ImageIO.read(File("./data/texture/player/1/up_01.png")),
                        ImageIO.read(File("./data/texture/player/1/up_02.png")),
                        ImageIO.read(File("./data/texture/player/1/up_03.png")),
                        ImageIO.read(File("./data/texture/player/1/down_01.png")),
                        ImageIO.read(File("./data/texture/player/1/down_02.png")),
                        ImageIO.read(File("./data/texture/player/1/down_03.png")),
                        ImageIO.read(File("./data/texture/player/1/left_01.png")),
                        ImageIO.read(File("./data/texture/player/1/left_02.png")),
                        ImageIO.read(File("./data/texture/player/1/left_03.png")),
                        ImageIO.read(File("./data/texture/player/1/right_01.png")),
                        ImageIO.read(File("./data/texture/player/1/right_02.png")),
                        ImageIO.read(File("./data/texture/player/1/right_03.png"))
                ),
                listOf(
                        ImageIO.read(File("./data/texture/player/2/up_01.png")),
                        ImageIO.read(File("./data/texture/player/2/up_02.png")),
                        ImageIO.read(File("./data/texture/player/2/up_03.png")),
                        ImageIO.read(File("./data/texture/player/2/down_01.png")),
                        ImageIO.read(File("./data/texture/player/2/down_02.png")),
                        ImageIO.read(File("./data/texture/player/2/down_03.png")),
                        ImageIO.read(File("./data/texture/player/2/left_01.png")),
                        ImageIO.read(File("./data/texture/player/2/left_02.png")),
                        ImageIO.read(File("./data/texture/player/2/left_03.png")),
                        ImageIO.read(File("./data/texture/player/2/right_01.png")),
                        ImageIO.read(File("./data/texture/player/2/right_02.png")),
                        ImageIO.read(File("./data/texture/player/2/right_03.png"))
                ),
                listOf(
                        ImageIO.read(File("./data/texture/player/3/up_01.png")),
                        ImageIO.read(File("./data/texture/player/3/up_02.png")),
                        ImageIO.read(File("./data/texture/player/3/up_03.png")),
                        ImageIO.read(File("./data/texture/player/3/down_01.png")),
                        ImageIO.read(File("./data/texture/player/3/down_02.png")),
                        ImageIO.read(File("./data/texture/player/3/down_03.png")),
                        ImageIO.read(File("./data/texture/player/3/left_01.png")),
                        ImageIO.read(File("./data/texture/player/3/left_02.png")),
                        ImageIO.read(File("./data/texture/player/3/left_03.png")),
                        ImageIO.read(File("./data/texture/player/3/right_01.png")),
                        ImageIO.read(File("./data/texture/player/3/right_02.png")),
                        ImageIO.read(File("./data/texture/player/3/right_03.png"))
                ),
                listOf(
                        ImageIO.read(File("./data/texture/player/4/up_01.png")),
                        ImageIO.read(File("./data/texture/player/4/up_02.png")),
                        ImageIO.read(File("./data/texture/player/4/up_03.png")),
                        ImageIO.read(File("./data/texture/player/4/down_01.png")),
                        ImageIO.read(File("./data/texture/player/4/down_02.png")),
                        ImageIO.read(File("./data/texture/player/4/down_03.png")),
                        ImageIO.read(File("./data/texture/player/4/left_01.png")),
                        ImageIO.read(File("./data/texture/player/4/left_02.png")),
                        ImageIO.read(File("./data/texture/player/4/left_03.png")),
                        ImageIO.read(File("./data/texture/player/4/right_01.png")),
                        ImageIO.read(File("./data/texture/player/4/right_02.png")),
                        ImageIO.read(File("./data/texture/player/4/right_03.png"))
                )
        )
        iExplosion = listOf(
                ImageIO.read(File("./data/texture/explosion/expl_06_0000.png")),
                ImageIO.read(File("./data/texture/explosion/expl_06_0001.png")),
                ImageIO.read(File("./data/texture/explosion/expl_06_0002.png")),
                ImageIO.read(File("./data/texture/explosion/expl_06_0003.png")),
                ImageIO.read(File("./data/texture/explosion/expl_06_0004.png")),
                ImageIO.read(File("./data/texture/explosion/expl_06_0005.png")),
                ImageIO.read(File("./data/texture/explosion/expl_06_0006.png")),
                ImageIO.read(File("./data/texture/explosion/expl_06_0007.png")),
                ImageIO.read(File("./data/texture/explosion/expl_06_0008.png")),
                ImageIO.read(File("./data/texture/explosion/expl_06_0009.png")),
                ImageIO.read(File("./data/texture/explosion/expl_06_0010.png")),
                ImageIO.read(File("./data/texture/explosion/expl_06_0011.png")),
                ImageIO.read(File("./data/texture/explosion/expl_06_0012.png")),
                ImageIO.read(File("./data/texture/explosion/expl_06_0013.png")),
                ImageIO.read(File("./data/texture/explosion/expl_06_0014.png")),
                ImageIO.read(File("./data/texture/explosion/expl_06_0015.png")),
                ImageIO.read(File("./data/texture/explosion/expl_06_0016.png")),
                ImageIO.read(File("./data/texture/explosion/expl_06_0017.png")),
                ImageIO.read(File("./data/texture/explosion/expl_06_0018.png")),
                ImageIO.read(File("./data/texture/explosion/expl_06_0019.png")),
                ImageIO.read(File("./data/texture/explosion/expl_06_0020.png")),
                ImageIO.read(File("./data/texture/explosion/expl_06_0021.png")),
                ImageIO.read(File("./data/texture/explosion/expl_06_0022.png")),
                ImageIO.read(File("./data/texture/explosion/expl_06_0023.png")),
                ImageIO.read(File("./data/texture/explosion/expl_06_0024.png")),
                ImageIO.read(File("./data/texture/explosion/expl_06_0025.png")),
                ImageIO.read(File("./data/texture/explosion/expl_06_0026.png")),
                ImageIO.read(File("./data/texture/explosion/expl_06_0027.png")),
                ImageIO.read(File("./data/texture/explosion/expl_06_0028.png")),
                ImageIO.read(File("./data/texture/explosion/expl_06_0029.png")),
                ImageIO.read(File("./data/texture/explosion/expl_06_0030.png")),
                ImageIO.read(File("./data/texture/explosion/expl_06_0031.png"))
        )
    }
}