package me.dl33

import kotlinx.serialization.*

@Serializable
class Trie {

    @Serializable
    private class Node(val children: MutableMap<Char, Node>)

    private val root = Node(mutableMapOf())

    // TODO: bulk add can be done faster
    fun add(word: String) {
        var current = root
        for(letter in word) {
            if(!current.children.containsKey(letter)) current.children[letter] = Node(mutableMapOf())
            current = current.children[letter]!!
        }
    }

    fun contains(word: String): Boolean {
        var current = root
        for(letter in word) {
            if(!current.children.containsKey(letter)) return false
            current = current.children[letter]!!
        }
        return true
    }
}
