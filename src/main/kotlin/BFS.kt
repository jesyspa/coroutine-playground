package jesyspa

fun <K, N> Graph<K, N>.bfs(
    start: K,
    visited: MutableSet<K> = mutableSetOf()
): Sequence<N> = sequence {
    if (start in visited) { return@sequence }
    visited.add(start)
    val node = lookup(start)
    yield(node)
    for (neighbor in neighbors(node)) {
        yieldAll(bfs(neighbor, visited))
    }
}
