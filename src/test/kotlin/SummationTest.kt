import jesyspa.asyncSum
import jesyspa.channelSum
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

abstract class SummationTest {
    abstract fun doSum(xs: List<Int>): Int

    @Test
    fun smallSumTest() {
        val data = listOf(1, 2, 3, 4, 5)
        assertEquals(15, doSum(data))
    }

    @Test
    fun bigSumTest() {
        val data = (1..10000).toList()
        assertEquals(50005000, doSum(data))
    }
}

class ChannelSumTest : SummationTest() {
    override fun doSum(xs: List<Int>): Int = channelSum(xs)
}

class AsyncSumTest : SummationTest() {
    override fun doSum(xs: List<Int>): Int = asyncSum(xs)
}