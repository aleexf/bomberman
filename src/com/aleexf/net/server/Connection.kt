package com.aleexf.net.server

import com.aleexf.logging.logger
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class Connection(val server:Server, val socket:Socket, val id:Int):Thread() {
    val nick:String
    private val iStream:BufferedReader
    private val oStream:PrintWriter
    init {
        iStream = BufferedReader(InputStreamReader(socket.getInputStream()))
        oStream = PrintWriter(socket.getOutputStream(), true)
        if (iStream.readLine() == "connected") {
            nick = iStream.readLine()
        } else {
            nick = "noname"
        }
        logger.info("[Server]: $nick $id")
        if (id == -1) {
            this.sendMessage("Server is full")
            this.close()
        } else {
            this.sendMessage(id.toString())
            this.start()
        }
        logger.info("[Server]: Nick $nick")
    }
    override fun run() {
        logger.info("[Server]: Process started")
        while (!socket.isClosed) {
            val message = iStream.readLine()
            logger.info("[Server]: Got message from $nick($id) '$message'")
            when (message) {
                "disconnect $id" -> this.close()
                "get_world_id" -> this.sendMessage(server.worldId.toString())
                else -> {
                    for (client in server.connections) {
                        client.sendMessage(message)
                    }
                }
            }
        }
    }
    fun sendMessage(msg:String) = oStream.println(msg)
    fun close() {
        iStream.close()
        oStream.close()
        socket.close()
        server.connections.remove(this)
        if (id != -1) {
            server.unusedIds.add(id)
        }
    }
}