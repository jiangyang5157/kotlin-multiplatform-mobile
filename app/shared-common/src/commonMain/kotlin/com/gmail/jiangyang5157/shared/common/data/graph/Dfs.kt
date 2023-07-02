package com.gmail.jiangyang5157.shared.common.data.graph

import com.gmail.jiangyang5157.shared.common.data.Stack
import com.gmail.jiangyang5157.shared.common.data.pop
import com.gmail.jiangyang5157.shared.common.data.push

fun <T> Graph<T>.dfs(
    nodeId: T,
    visit: (node: Node<T>) -> Boolean,
) {
    if (node(nodeId) == null) throw IllegalArgumentException("Node id $nodeId not found")

    dfs(Stack.from(nodeId), visit, hashMapOf())
}

fun <T> Graph<T>.dfs(
    stack: Stack<T>,
    visit: (node: Node<T>) -> Boolean,
    visited: HashMap<T, Boolean>,
) {
    val id = stack.lastOrNull()?.value ?: return
    val node = node(id) ?: return

    visited[id] = true
    val stop = visit(node)
    if (stop) {
        stack.pop()
        return
    }

    val targets = targets(id) ?: run {
        stack.pop()
        return
    }

    targets.forEach { target ->
        val visitedTarget = visited[target.key]
        if (visitedTarget != true) {
            stack push target.key
            dfs(stack, visit, visited)
        }
    }

    stack.pop()
}