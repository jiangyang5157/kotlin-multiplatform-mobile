package com.gmail.jiangyang5157.shared.common.data.graph

/**
 * Created by Yang Jiang on June 28, 2017
 */
open class Graph<T> {

    /**
     * nodes HashMap<Node.id, Node> stores all nodes.
     */
    private val nodes: HashMap<T, Node<T>> = HashMap()

    /**
     * sourcesMap HashMap<Node.id, HashMap<Node.id, Edge>>  maps a node to parents with edge.
     */
    private val sourcesMap: HashMap<T, HashMap<T, Edge>> = HashMap()

    /**
     * targetsMap HashMap<Node.id, HashMap<Node.id, Edge>>  maps a node to children with edge.
     */
    private val targetsMap: HashMap<T, HashMap<T, Edge>> = HashMap()

    override fun toString(): String {
        return "Graph(\n\t\tnodes=$nodes,\n\t\tsources=$sourcesMap,\n\t\ttargets=$targetsMap,\n)"
    }

    fun size(): Int = nodes.size

    fun node(id: T): Node<T>? = nodes[id]

    fun sources(id: T): HashMap<T, Edge>? = sourcesMap[id]

    fun targets(id: T): HashMap<T, Edge>? = targetsMap[id]

    fun edge(src: T, tgt: T): Edge? {
        val children = targetsMap[src] ?: return null
        return children[tgt]
    }

    fun addNode(node: Node<T>) {
        nodes[node.id] = node
    }

    fun deleteNode(id: T) {
        nodes.remove(id)
        sourcesMap.remove(id)
        targetsMap.remove(id)

        // remove edges which source is the node
        sourcesMap.forEach {
            it.value.remove(id)
        }

        // remove edges which target is the node
        targetsMap.forEach {
            it.value.remove(id)
        }
    }

    fun addEdge(src: T, tgt: T, edge: Edge) {
        if (sourcesMap[tgt] == null) {
            sourcesMap[tgt] = HashMap()
        }

        if (targetsMap[src] == null) {
            targetsMap[src] = HashMap()
        }
        sourcesMap[tgt]!![src] = edge
        targetsMap[src]!![tgt] = edge
    }

    fun deleteEdge(src: T, tgt: T) {
        sourcesMap[tgt]?.remove(src)
        targetsMap[src]?.remove(tgt)
    }
}
