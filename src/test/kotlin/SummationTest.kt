import jesyspa.channelSum
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SummationTest {
    @Test
    fun channelSumTest() {
        val data = listOf(1, 2, 3, 4, 5)
        assertEquals(15, channelSum(data))
    }

    @Test
    fun bigChannelSumTest() {
        val data = (1..10000).toList()
        assertEquals(50005000, channelSum(data))
    }
}