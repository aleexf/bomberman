package com.aleexf.gui

import java.awt.TextField
import java.awt.event.KeyEvent
import java.awt.event.KeyAdapter
import java.awt.event.FocusEvent
import java.awt.event.FocusListener


class NicknameField(val x:Int, val y:Int, val width:Int, val height:Int):KeyAdapter() {
    val textField = TextField("Enter your nickname...")
    private val maxlength = 12
    private var lastCorrectText = ""
    init {
        textField.setBounds(x, y, width, height)
        textField.addKeyListener(this)
        textField.addFocusListener(object: FocusListener {
            override fun focusGained(e: FocusEvent?) {
                if (textField.text != "Enter your nickname...") return
                textField.text = ""
            }
            override fun focusLost(e: FocusEvent?) {
                if (textField.text.isNotEmpty()) return
                textField.text = "Enter your nickname..."
            }
        })
    }
    override fun keyPressed(e:KeyEvent) {
        if (isCorrect(textField.text)) {
            lastCorrectText = textField.text
        } else {
            textField.text = lastCorrectText
        }
    }
    private fun isCorrect(str:String) =
            str.count {it in 'a'..'z' || it in 'A'..'Z'
                || it in '0'..'9' || it == '.' || it == '-' || it == '_'} == str.length
}