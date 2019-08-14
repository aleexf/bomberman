package com.aleexf.net.server

import java.net.Socket
import java.io.PrintWriter
import java.io.BufferedReader
import java.io.InputStreamReader

import com.aleexf.logging.LoggingFactories

class Connection(val server: Server, val socket: Socket, val id: Int) : Thread() {
    private val nick: String
    private val iStream: BufferedReader
    private val oStream: PrintWriter
    private val logger by lazy { LoggingFactories.serverFactory.getLogger(this.javaClass.name) }
    init {
        iStream = BufferedReader(InputStreamReader(socket.getInputStream()))
        oStream = PrintWriter(socket.getOutputStream(), true)
        if (iStream.readLine() == "connected") {
            nick = iStream.readLine()
        } else {
            nick = "noname"
        }
        logger.info("$nick connected with id $id")
        if (id == -1) {
            this.sendMessage("Server is full")
            this.close()
        } else {
            this.sendMessage(id.toString())
            this.start()
        }
    }
    override fun run() {
        sleep(100)
        while (!socket.isClosed) {
            val message = iStream.readLine()
            when (message) {
                "disconnect $id" -> this.close()
                "get_world_id" -> this.sendMessage(server.worldId.toString())
                "game init" -> {
                    server.connections.forEach {
                        if (it != this) sendMessage("player connected ${it.nick} ${it.id}")
                    }
                    server.resendMessage("player connected $nick $id")
                }
                else -> {
                    server.resendMessage(message)
                }
            }
        }
        this.close()
    }
    fun sendMessage(msg:String) = oStream.println(msg)
    fun close() {
        iStream.close()
        oStream.close()
        socket.close()
        server.connections.remove(this)
        server.resendMessage("player disconnected $id")
        if (id != -1) {
            server.unusedIds.add(id)
        }
    }
}