package com.aleexf.net.server

import java.net.Socket
import java.io.PrintWriter
import java.io.BufferedReader
import java.io.InputStreamReader

import com.aleexf.logging.Logger

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
        Logger.info("[Server]: $nick connected with id $id")
        if (id == -1) {
            this.sendMessage("Server is full")
            this.close()
        } else {
            this.sendMessage(id.toString())
            this.start()
        }
    }
    override fun run() {
        while (!socket.isClosed) {
            val message = iStream.readLine()
            when (message) {
                "disconnect $id" -> this.close()
                "get_world_id" -> this.sendMessage(server.worldId.toString())
                "game init" -> {
                    server.connections.forEach{
                        it.sendMessage("player connected ${nick} ${id}")
                        if (it != this) sendMessage("player connected ${it.nick} ${it.id}")
                    }
                }
                else -> {
                    server.connections.forEach{it.sendMessage(message)}
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