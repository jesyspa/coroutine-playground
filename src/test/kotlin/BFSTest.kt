import jesyspa.ConcreteGraph
import jesyspa.ConcreteNode
import jesyspa.Graph
import jesyspa.bfs
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class BFSTest {
    @Test
    fun testGraphSum() {
        val graph = ConcreteGraph(
            ConcreteNode(10, listOf(1, 2)),
            ConcreteNode(10, listOf(0)),
            ConcreteNode(30, listOf(1)),
            // Unreachable node
            ConcreteNode(40, listOf(0)),
        )

        val actual = graph.bfs(0).sumOf { it.data }
        assertEquals(50, actual)
    }

    @Test
    fun testInfiniteGraphBfs() {
        val graph = object : Graph<Int, Int> {
            override fun lookup(key: Int): Int = key
            override fun neighbors(node: Int): List<Int> = listOf(2*node, 2*node + 1)
        }

        val actual = graph.bfs(1).first { it > 100 }
        assertEquals(128, actual)
    }
}