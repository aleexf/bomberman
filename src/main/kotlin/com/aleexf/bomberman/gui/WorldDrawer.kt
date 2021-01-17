package com.aleexf.bomberman.gui

import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext

import com.aleexf.bomberman.config.ClientConfig
import com.aleexf.bomberman.game.world.GameWorld
import javafx.scene.image.Image

class WorldDrawer(
    private val world: GameWorld,
    private val canvas: Canvas
) : Thread() {
    private val textureManager: TextureManager = TextureManager(mapOf("floor" to "textures/floor.png"))
    private val cellSize: Double = ClientConfig.getInstance().cellSize
    var isActive: Boolean = false


    override fun run() {
        isActive = true
        while (isActive) {
            val graphics: GraphicsContext = canvas.graphicsContext2D
            //! draw background
            val floorImg: Image = textureManager.textures["floor"]!!
            for (i in 0 until world.rows) {
                for (j in 0 until world.cols) {
                    graphics.drawImage(floorImg,
                        0.0, 0.0,
                        floorImg.width, floorImg.height,
                        i * cellSize, j * cellSize,
                        cellSize, cellSize)
                    graphics.fillText((i * world.cols + j).toString(), i * cellSize, j * cellSize)
                }
            }

            sleep(1000L / ClientConfig.getInstance().frameRate)
        }
    }
}
