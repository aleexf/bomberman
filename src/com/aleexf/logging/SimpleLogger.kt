package com.aleexf.logging

import java.util.Date
import java.io.FileWriter
import java.io.PrintWriter
import java.text.SimpleDateFormat

class Logger(val filename:String) {
    private val formatter = SimpleDateFormat("[dd.MM.yyyy HH:mm:ss]")
    private val oStream = PrintWriter(FileWriter(filename, false), true)
    private fun getDate() = formatter.format(Date())
    fun info(msg:String) = oStream.println("${getDate()}[INFO]: $msg")
    fun warning(msg:String) = oStream.println("${getDate()}[WARNING]: $msg")
    fun error(msg:String) = oStream.println("${getDate()}[ERROR]: $msg")
}

val logger = Logger(".\\logfile.log")