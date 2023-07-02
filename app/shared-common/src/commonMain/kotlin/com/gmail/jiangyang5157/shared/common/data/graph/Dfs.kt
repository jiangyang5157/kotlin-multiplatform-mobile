package com.gmail.jiangyang5157.shared.common.data.graph

import com.gmail.jiangyang5157.shared.common.data.stack.Stack
import com.gmail.jiangyang5157.shared.common.data.stack.clear
import com.gmail.jiangyang5157.shared.common.data.stack.pop
import com.gmail.jiangyang5157.shared.common.data.stack.push

class Dfs<T>() {

    private var stack = Stack.empty<T>()

    /**
     * @param visit returns True to stop
     */
    fun dfs(graph: Graph<T>, nodeId: T, visit: (node: Node<T>) -> Boolean) {
        if (graph.node(nodeId) == null) throw IllegalArgumentException("Node id $nodeId not found")

        stack = stack.push(nodeId)
        dfs(graph, visit, HashMap())
        stack = stack.clear()
    }

    private fun dfs(
        graph: Graph<T>, visit: (node: Node<T>) -> Boolean, visited: HashMap<T, Boolean>
    ) {
        if (stack.elements.isEmpty()) return

        val id = stack.elements.last().value
        val node = graph.node(id) ?: return

        visited[id] = true
        if (visit(node)) {
            stack.pop()
            return
        }

        val targets = graph.targets(id)
        if (targets == null) {
            stack.pop()
            return
        }

        targets.forEach { target ->
            val visitedTarget = visited[target.key]
            if (visitedTarget != true) {
                stack = stack push target.key
                dfs(graph, visit, visited)
            }
        }
        stack = stack.pop()
    }
}