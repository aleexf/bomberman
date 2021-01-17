package com.aleexf.bomberman.gui

import java.io.InputStream
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.layout.AnchorPane
import javafx.stage.Stage

import com.aleexf.bomberman.game.Game
import com.aleexf.bomberman.game.world.GameWorld

class Client : Application() {
    lateinit var anchorPane: AnchorPane
    lateinit var gameFieldCanvas: Canvas
    lateinit var worldDrawer: WorldDrawer

    override fun start(primaryStage: Stage?) {
        val fxmlLoader = FXMLLoader()
        val fxmlStream: InputStream? = this::class.java.classLoader.getResourceAsStream("ui/GameScreen.fxml")

        anchorPane = fxmlLoader.load(fxmlStream!!)
        val scene = Scene(anchorPane)
        gameFieldCanvas = scene.lookup("#gameFieldCanvas") as Canvas

        primaryStage!!.let {
            it.title = "Bomberman game"
            it.scene = scene
            it.show()
        }

        worldDrawer = WorldDrawer(GameWorld(), gameFieldCanvas)
        worldDrawer.start()
    }

    override fun stop() {
        super.stop()
        worldDrawer.isActive = false
    }
}
