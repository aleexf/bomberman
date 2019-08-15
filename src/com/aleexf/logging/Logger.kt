package com.aleexf.logging

class Logger(private val className: String,
             private val factory: LoggerFactory
) {
    fun info(msg: String) = factory.log("INFO at $className - $msg")
    fun warning(msg: String) = factory.log("WARNING at $className - $msg")
    fun error(msg: String) = factory.log("ERROR at $className - $msg")
}