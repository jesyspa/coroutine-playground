package jesyspa

interface Graph<K, N> {
    fun lookup(key: K): N
    fun neighbors(node: N): List<K>
}

class ConcreteNode(val data: Int, val neighbors: List<Int>)
class ConcreteGraph(val nodes: List<ConcreteNode>) : Graph<Int, ConcreteNode> {
    constructor(vararg nodes: ConcreteNode) : this(nodes.toList())

    override fun lookup(key: Int): ConcreteNode = nodes[key]
    override fun neighbors(node: ConcreteNode): List<Int> = node.neighbors
}

