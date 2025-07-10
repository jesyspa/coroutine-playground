package jesyspa

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlin.math.min

const val workerCount = 2

fun channelSum(xs: List<Int>): Int = runBlocking {
    val resultChannel = Channel<Int>()

    fun CoroutineScope.sumWorker(start: Int, end: Int) {
        launch {
            var localResult = 0
            for (i in start until end) {
                localResult += xs[i]
            }
            resultChannel.send(localResult)
        }
    }

    val chunkSize = getChunkSize(xs)
    for (i in 0 until workerCount) {
        val (start, end) = determineRange(i, chunkSize, xs)
        sumWorker(start, end)
    }

    var result = 0
    repeat(workerCount) {
        result += resultChannel.receive()
    }
    result
}

fun asyncSum(xs: List<Int>): Int = runBlocking {
    val chunkSize = getChunkSize(xs)

    val deferreds = (0 until workerCount).map { i ->
        async {
            var localResult = 0
            val (start, end) = determineRange(i, chunkSize, xs)
            for (j in start until end) {
                localResult += xs[j]
            }
            localResult
        }
    }

    deferreds.awaitAll().sum()
}

private fun getChunkSize(xs: List<Int>): Int =
    xs.size / workerCount + if (xs.size % workerCount == 0) 0 else 1

private fun determineRange(i: Int, chunkSize: Int, xs: List<Int>): Pair<Int, Int> {
    val start = i * chunkSize
    val end = min((i + 1) * chunkSize, xs.size)
    return Pair(start, end)
}
