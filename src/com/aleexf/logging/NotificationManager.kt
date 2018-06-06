package com.aleexf.logging

import javax.swing.JOptionPane

object NotificationManager {
    fun error(title:String, msg:String) {
        JOptionPane.showMessageDialog(null,
                msg,
                title,
                JOptionPane.ERROR_MESSAGE)
    }
    fun warning(title:String, msg:String) {
        JOptionPane.showMessageDialog(null,
                msg,
                title,
                JOptionPane.ERROR_MESSAGE)
    }
}