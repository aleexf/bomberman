package com.aleexf.bomberman.utils

import com.google.gson.GsonBuilder
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory

import com.aleexf.bomberman.game.world.objects.attributes.*
import com.google.gson.Gson

object Serializator {
    private val gsonBuilder: GsonBuilder

    init {
        val attributeAdapterFactory = RuntimeTypeAdapterFactory.of(Attribute::class.java)
            .registerSubtype(Movable::class.java)

        gsonBuilder = GsonBuilder().registerTypeAdapterFactory(attributeAdapterFactory)
    }

    val instance: Gson
        get() = synchronized(this) { gsonBuilder.create() }
}
