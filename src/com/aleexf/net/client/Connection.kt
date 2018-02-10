package com.aleexf.net.client

import com.aleexf.logging.logger
import java.net.Socket
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter


class Connection(val nick:String, val ip:String) {
    val socket:Socket
    val localId:Int
    private val port = 8888
    private val iStream:BufferedReader
    private val oStream:PrintWriter
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
    fun sendMessage(str:String) = oStream.println(str);
    fun getMessage() = iStream.readLine()
}