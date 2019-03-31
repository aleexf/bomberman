package com.aleexf.net.server

import java.net.Socket
import java.net.ServerSocket

import com.aleexf.config.Config
import com.aleexf.logging.Logger
import com.aleexf.logging.LoggingFactories

open class Server(var worldId: Int) : Thread() {
    val connections: MutableList<Connection> = mutableListOf()
    val unusedIds: MutableList<Int> = mutableListOf(0, 1, 2, 3)

    private val port = Config.Port
    private val server: ServerSocket = ServerSocket(port)
    private val logger by lazy { LoggingFactories.serverFactory.getLogger(this.javaClass.name) }

    init {
        isDaemon = true
    }
    override fun run() {
        while (true) {
            val client = server.accept()
            logger.info("Got new connection")
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
        connections.forEach { it.close() }
        server.close()
    }
}