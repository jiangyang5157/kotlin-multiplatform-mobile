package com.gmail.jiangyang5157.kit.data.graph

/**
 * Created by Yang Jiang on June 28, 2017
 */
open class Node<out T>(val id: T) {

    override fun toString(): String {
        return "Node(id=$id)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Node<*>) return false
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
