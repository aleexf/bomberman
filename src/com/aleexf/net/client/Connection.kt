package com.aleexf.net.client

import java.net.Socket
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter

import com.aleexf.config.Config
import com.aleexf.logging.LoggingFactories

class Connection(nick: String, val ip: String) {
    val localId: Int
    private val socket: Socket
    private val port = Config.Port
    private val iStream: BufferedReader
    private val oStream: PrintWriter
    private val logger by lazy { LoggingFactories.clientFactory.getLogger(this.javaClass.name) }
    init {
        socket = Socket(ip, port)
        iStream = BufferedReader(InputStreamReader(socket.getInputStream()))
        oStream = PrintWriter(socket.getOutputStream(), true)
        this.sendMessage("connected\n$nick")
        val msg = this.getMessage()
        if (msg == "Server is full") {
            logger.info("Server is full")
            localId = -1
            System.exit(0)
        } else {
            localId = msg.toInt()
        }
    }
//    fun sendMessage(str: String) = oStream.println(str)
    fun sendMessage(str: String) {
        logger.info("Sending \"$str\"")
        oStream.println(str)
    }
    fun getMessage() = iStream.readLine()
}