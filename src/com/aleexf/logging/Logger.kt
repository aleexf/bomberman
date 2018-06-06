package com.aleexf.logging

import java.util.Date
import java.io.FileWriter
import java.io.PrintWriter
import java.text.SimpleDateFormat

import com.aleexf.logging.NotificationManager

object Logger {
    private val formatter = SimpleDateFormat("[dd.MM.yyyy HH:mm:ss]")
    private val oStream = PrintWriter(FileWriter("./logs.log", false), true)
    private fun getDate() = formatter.format(Date())
    fun info(msg:String) = oStream.println("${getDate()}[INFO]: $msg")
    fun warning(title:String = "W", msg:String, silent:Boolean = true) {
        oStream.println("${getDate()}[WARNING]: ${title + ": " + msg}")
        if (!silent) NotificationManager.warning(title, msg)
    }
    fun error(title:String = "E", msg:String, silent:Boolean = true) {
        oStream.println("${getDate()}[ERROR]: ${title + ": " + msg}")
        if (!silent) NotificationManager.error(title, msg)
    }
}