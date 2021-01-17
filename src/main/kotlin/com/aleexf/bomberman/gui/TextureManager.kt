package com.aleexf.bomberman.gui

import javafx.scene.image.Image

class TextureManager(requiredTextures: Map<String, String>) {
    val textures: MutableMap<String, Image> = hashMapOf()

    init {
        for ((textureName, uri) in requiredTextures) {
            textures[textureName] = Image(this::class.java.classLoader.getResourceAsStream(uri)!!)
        }
    }
}
