package com.aleexf.bomberman.config

import java.io.FileInputStream
import java.io.FileNotFoundException

import com.google.gson.Gson

class ServerConfig {
    var tickRate: Int = 32

    companion object {
        private const val CONFIG_PATH = "./data/server-config.json"
        var INSTANCE: ServerConfig? = null

        fun getInstance(): ServerConfig {
            return INSTANCE ?: synchronized(this) {
                return loadConfig().also { INSTANCE = it }
            }
        }

        fun loadConfig(): ServerConfig {
            return try {
                Gson().fromJson(FileInputStream(CONFIG_PATH).bufferedReader(), ServerConfig::class.java)
            } catch (exc: FileNotFoundException) {
                // TODO: log error
                ServerConfig()
            }
        }
    }
}
