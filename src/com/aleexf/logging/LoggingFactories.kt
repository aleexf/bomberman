package com.aleexf.logging

object LoggingFactories {
    val clientFactory = LoggerFactory("client")
    val serverFactory = LoggerFactory("server")
}