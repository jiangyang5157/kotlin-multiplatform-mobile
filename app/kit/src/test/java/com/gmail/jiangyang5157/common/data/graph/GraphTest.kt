package com.gmail.jiangyang5157.common.data.graph

import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

/**
 * Created by Yang Jiang on June 30, 2017
 */
class GraphTest {

    /*
    https://github.com/jiangyang5157/go-graph/tree/master/testdata/graph_03.png
    "graph_03": {
        "S": {
            "B": 14
        },
        "A": {
            "S": 15,
            "B": 5,
            "D": 20,
            "T": 44
        },
        "B": {
            "S": 14,
            "A": 5,
            "D": 30,
            "E": 18
        },
        "C": {
            "S": 9,
            "E": 24
        },
        "D": {
            "A": 20,
            "B": 30,
            "E": 2,
            "F": 11,
            "T": 16
        },
        "E": {
            "B": 18,
            "C": 24,
            "D": 2,
            "F": 6,
            "T": 19
        },
        "F": {
            "D": 11,
            "E": 6,
            "T": 6
        },
        "T": {
            "A": 44,
            "D": 16,
            "F": 6,
            "E": 19
        }
    },
     */
    @org.junit.Test
    fun test_graph() {
        val graph =
            Graph<String>()

        graph.addNode(Node("S"))
        graph.addNode(Node("A"))
        graph.addNode(Node("B"))
        graph.addNode(Node("C"))
        graph.addNode(Node("D"))
        graph.addNode(Node("E"))
        graph.addNode(Node("F"))
        graph.addNode(Node("T"))

        graph.addEdge(
            "S", "B",
            Edge(14.0)
        )

        graph.addEdge(
            "A", "S",
            Edge(15.0)
        )
        graph.addEdge(
            "A", "B",
            Edge(5.0)
        )
        graph.addEdge(
            "A", "D",
            Edge(20.0)
        )
        graph.addEdge(
            "A", "T",
            Edge(44.0)
        )

        graph.addEdge(
            "B", "S",
            Edge(14.0)
        )
        graph.addEdge(
            "B", "A",
            Edge(5.0)
        )
        graph.addEdge(
            "B", "D",
            Edge(30.0)
        )
        graph.addEdge(
            "B", "E",
            Edge(18.0)
        )

        graph.addEdge(
            "C", "S",
            Edge(9.0)
        )
        graph.addEdge(
            "C", "E",
            Edge(24.0)
        )

        graph.addEdge(
            "D", "A",
            Edge(20.0)
        )
        graph.addEdge(
            "D", "B",
            Edge(30.0)
        )
        graph.addEdge(
            "D", "E",
            Edge(2.0)
        )
        graph.addEdge(
            "D", "F",
            Edge(11.0)
        )
        graph.addEdge(
            "D", "T",
            Edge(16.0)
        )

        graph.addEdge(
            "E", "B",
            Edge(18.0)
        )
        graph.addEdge(
            "E", "C",
            Edge(24.0)
        )
        graph.addEdge(
            "E", "D",
            Edge(2.0)
        )
        graph.addEdge(
            "E", "F",
            Edge(6.0)
        )
        graph.addEdge(
            "E", "T",
            Edge(19.0)
        )

        graph.addEdge(
            "F", "D",
            Edge(11.0)
        )
        graph.addEdge(
            "F", "E",
            Edge(6.0)
        )
        graph.addEdge(
            "F", "T",
            Edge(6.0)
        )

        graph.addEdge(
            "T", "A",
            Edge(44.0)
        )
        graph.addEdge(
            "T", "D",
            Edge(16.0)
        )
        graph.addEdge(
            "T", "F",
            Edge(6.0)
        )
        graph.addEdge(
            "T", "E",
            Edge(19.0)
        )

        println(graph)

        assertEquals(8, graph.nodes.size)
        assertEquals(3, graph.sourcesMap["A"]?.size)
        assertEquals(4, graph.targetsMap["A"]?.size)
        assertNotNull(graph.getEdge("A", "B"))

        graph.deleteEdge("A", "B")
        assertEquals(8, graph.nodes.size)
        assertEquals(3, graph.sourcesMap["A"]?.size)
        assertEquals(3, graph.targetsMap["A"]?.size)
        assertNull(graph.getEdge("A", "B"))

        graph.deleteNode("A")
        assertEquals(7, graph.nodes.size)
        assertNull(graph.getNode("A"))
        assertNull(graph.sourcesMap["A"])
        assertNull(graph.targetsMap["A"])
    }
}
