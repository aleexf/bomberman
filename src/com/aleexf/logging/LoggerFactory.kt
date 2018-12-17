package com.aleexf.logging

import java.util.Date
import java.io.FileWriter
import java.io.PrintWriter
import java.text.SimpleDateFormat

class LoggerFactory(logfile: String) {
    private val formatter = SimpleDateFormat("[dd.MM.yyyy HH:mm:ss]")
    private val oStream = PrintWriter(FileWriter("$logfile.log", false), true)

    private fun getDate() = formatter.format(Date())

    fun log(message: String) = oStream.println("[${getDate()}]$message")
    fun getLogger(className: String) = Logger(className, this)
}