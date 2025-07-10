package jesyspa

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.math.min

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

    val workerCount = 2
    val chunkSize = xs.size / workerCount + if (xs.size % workerCount == 0) 0 else 1
    for (i in 0 until workerCount) {
        sumWorker(i * chunkSize, min((i + 1) * chunkSize, xs.size))
    }

    var result = 0
    repeat(workerCount) {
        result += resultChannel.receive()
    }
    result
}