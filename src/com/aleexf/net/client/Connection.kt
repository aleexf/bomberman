package com.aleexf.net.client

import com.aleexf.game.world.GameWorld

import java.net.Socket
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter

val port = 8888

class Connection(val nick:String, val ip:String) {
    val socket:Socket
    val iStream:BufferedReader
    val oStream:PrintWriter
    init {
        socket = Socket(ip, port)
        iStream = BufferedReader(InputStreamReader(socket.getInputStream()))
        oStream = PrintWriter(socket.getOutputStream(), true)
        oStream.println("connected\n$nick")
    }
    fun sendMessage(str:String) {
        oStream.println(str);
    }
    fun getMessage() = iStream.readLine()
    fun getInt() = iStream.read()
}