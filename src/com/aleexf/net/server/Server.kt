package com.aleexf.net.server

import java.net.Socket
import java.net.ServerSocket

import com.aleexf.logging.Logger

open class Server(var worldId:Int):Thread() {
    val connections:MutableList<Connection> = mutableListOf()
    val unusedIds:MutableList<Int> = mutableListOf(0, 1, 2, 3)
    private val port = 8888
    private var server:ServerSocket = ServerSocket(port)
    init {
        isDaemon = true
    }
    override fun run() {
        while (true) {
            val client:Socket = server.accept()
            Logger.info("[Server]: Got new connection")
            var clientId = -1
            if (unusedIds.isNotEmpty()) {
                clientId = unusedIds[0]
                unusedIds.removeAt(0)
            }
            connections.add(Connection(this, client, clientId))
        }
    }
    open fun resendMessage(msg:String) {
        connections.forEach { it.sendMessage(msg) }
    }
    fun closeConnections() {
        connections.forEach{ it.close() }
        server.close()
    }
}