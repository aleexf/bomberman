package com.aleexf.net.server

import java.net.Socket
import java.net.ServerSocket

import com.aleexf.logging.logger

class Server(val worldId:Int):Thread() {
    val connections:MutableList<Connection> = mutableListOf()
    val unusedIds:MutableList<Int> = mutableListOf(1, 2, 3, 4)
    private val port = 8888
    private var server:ServerSocket = ServerSocket(port)
    init {
        isDaemon = true
    }
    override fun run() {
        while (true) {
            val client:Socket = server.accept()
            logger.info("[Server]: Got new connection")
            var clientId = -1
            if (unusedIds.isNotEmpty()) {
                clientId = unusedIds[0]
                unusedIds.removeAt(0)
            }
            connections.add(Connection(this, client, clientId))
        }
    }
    fun closeConnections() {
        for (con in connections) {
            con.close()
        }
        server.close()
    }
}
