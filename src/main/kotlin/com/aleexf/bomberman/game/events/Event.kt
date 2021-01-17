package com.aleexf.bomberman.game.events

data class Event(val producer: String?,
                 val details: HashMap<String, String?>) {
    val timestamp: Long = System.currentTimeMillis()
}
