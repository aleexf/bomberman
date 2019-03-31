package com.aleexf.utils.xml

import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

import org.w3c.dom.Node
import org.w3c.dom.NodeList

class XMLObject() {

    constructor(file: File): this(dInstance.newDocumentBuilder().parse(file).childNodes)

    private constructor(list: NodeList): this() {
        for (i in 0 until list.length) {
            val node = list.item(i)
            if (node.nodeType != Node.ELEMENT_NODE) continue
            if (node.childNodes.length == 1) {
                children.add(Pair(node.nodeName, XMLObject(node.textContent)))
            } else {
                children.add(Pair(node.nodeName, XMLObject(node.childNodes)))
            }
        }
    }

    private constructor(content: String):this() {
        this.value = content
    }

    private var value: String? = null
    private val children: MutableList<Pair<String, XMLObject>> = mutableListOf()

    companion object {
        private val dInstance = DocumentBuilderFactory.newInstance()

        fun content(obj: XMLObject) = obj.value
        fun content(array: Array<XMLObject>): Array<String?> {
            val list = mutableListOf<String?>()
            for (obj in array) list.add(content(obj))
            return list.toTypedArray()
        }

        fun contentToInt(obj: XMLObject) = obj.value?.toIntOrNull()
        fun contentToInt(array: Array<XMLObject>): Array<Int?> {
            val list = mutableListOf<Int?>()
            for (obj in array) list.add(contentToInt(obj))
            return list.toTypedArray()
        }
    }

    operator fun get(key: String) = children.firstOrNull {it.first == key}?.second ?: XMLObject()
    fun getAllEntries(key: String): Array<XMLObject> {
        val entries = children.filter { it.first == key }
        val objects = mutableListOf<XMLObject>()
        for((_, value) in entries) {
            objects.add(value)
        }
        return objects.toTypedArray()
    }
    fun containsTag(tagName: String) = children.any { it.first == tagName }
}