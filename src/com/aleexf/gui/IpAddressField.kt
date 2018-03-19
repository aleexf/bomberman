package com.aleexf.gui

import kotlin.math.max
import java.awt.TextField
import java.awt.event.KeyEvent
import java.awt.event.KeyAdapter


class IpAddressField(val x:Int, val y:Int, val width:Int, val height:Int):KeyAdapter() {
    val textField:TextField = TextField()
    private val IpPattern = "***.***.***.***"
    private var text = StringBuilder(IpPattern)
    private var LastPosition = 0
    init {
        textField.addKeyListener(this)
        textField.setBounds(x, y, width, height)
        textField.isEditable = false
        textField.text = IpPattern
    }
    override fun keyPressed(e: KeyEvent) {
        textField.text = text.toString()
        if (e.keyCode == KeyEvent.VK_BACK_SPACE) {
            LastPosition = max(LastPosition-1, 0)
            if ((LastPosition+1)%4 == 0) {
                --LastPosition
            }
            text[LastPosition] = IpPattern[LastPosition]
            textField.text = text.toString()
        }
        if (e.keyChar !in '0'..'9') return
        if (LastPosition > IpPattern.length) return
        text[LastPosition] = e.keyChar
        ++LastPosition
        if ((LastPosition+1) % 4 == 0) {
            ++LastPosition
        }
        textField.text = text.toString()
    }

}