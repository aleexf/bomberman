package com.aleexf.bomberman

import javafx.application.Application

import com.aleexf.bomberman.config.ClientConfig
import com.aleexf.bomberman.config.ServerConfig
import com.aleexf.bomberman.gui.Client

fun main(args: Array<String>) {
    ClientConfig.loadConfig()
    ServerConfig.loadConfig()

    Application.launch(Client::class.java, *args)
}
