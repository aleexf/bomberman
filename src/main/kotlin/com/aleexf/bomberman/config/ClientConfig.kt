package com.aleexf.bomberman.config

import java.io.FileInputStream
import java.io.FileNotFoundException

import com.google.gson.Gson

class ClientConfig {

    var frameRate: Int = 60
    var cellSize: Double = 32.0

    companion object {
        private const val CONFIG_PATH = "./data/client-config.json"
        var INSTANCE: ClientConfig? = null

        fun getInstance(): ClientConfig {
            return INSTANCE ?: synchronized(this) {
                return loadConfig().also { INSTANCE = it }
            }
        }

        fun loadConfig(): ClientConfig {
            return try {
                Gson().fromJson(FileInputStream(CONFIG_PATH).bufferedReader(), ClientConfig::class.java)
            } catch (exc: FileNotFoundException) {
                // TODO: log error
                ClientConfig()
            }
        }
    }
}
