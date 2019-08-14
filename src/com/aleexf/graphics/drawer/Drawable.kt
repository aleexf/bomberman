package com.aleexf.graphics.drawer

import java.awt.Image
import java.awt.Graphics

interface Drawable {
    var tSizeX: Int
    var tSizeY: Int
    var rSizeX: Int
    var rSizeY: Int
    val priority: Int
    var texture: Image

    fun drawIt(graph: Graphics)
}