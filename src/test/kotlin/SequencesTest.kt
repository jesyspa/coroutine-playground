import jesyspa.powersOf2
import jesyspa.powersOf2Capturing
import jesyspa.periodic
import jesyspa.counts
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class SequencesTest {
    @Test
    fun testPowersOf2() {
        val powers = powersOf2().toList()
        
        // Check size
        assertEquals(32, powers.size)
        
        // Check first few values
        assertEquals(1, powers[0])
        assertEquals(2, powers[1])
        assertEquals(4, powers[2])
        assertEquals(8, powers[3])
        
        // Check that each element is double the previous one
        for (i in 1 until powers.size) {
            assertEquals(powers[i-1] * 2, powers[i])
        }
        
        // Check last value (2^31)
        assertEquals(1 shl 31, powers.last())
    }
    
    @Test
    fun testPowersOf2Capturing() {
        val powers = powersOf2Capturing().toList()
        
        // Check size
        assertEquals(32, powers.size)
        
        // Check first few values
        assertEquals(1, powers[0])
        assertEquals(2, powers[1])
        assertEquals(4, powers[2])
        assertEquals(8, powers[3])
        
        // Check that each element is double the previous one
        for (i in 1 until powers.size) {
            assertEquals(powers[i-1] * 2, powers[i])
        }
        
        // Check last value (2^31)
        assertEquals(1 shl 31, powers.last())
    }
    
    @Test
    fun testPeriodic() {
        val periodicSequence = periodic().take(10).toList()
        
        // Check pattern
        val expected = listOf(1, 2, 1, 2, 1, 2, 1, 2, 1, 2)
        assertEquals(expected, periodicSequence)
    }
    
    @Test
    fun testCountsWithZero() {
        val count = counts(0).toList()
        
        // Should be empty for n=0
        assertTrue(count.isEmpty())
    }
    
    @Test
    fun testCountsWithOne() {
        val count = counts(1).toList()
        
        // Should contain just [0]
        assertEquals(listOf(0), count)
    }
    
    @Test
    fun testCountsWithThree() {
        val count = counts(3).toList()
        
        // Should contain [0, 1, 2, 0, 1, 0]
        val expected = listOf(0, 1, 2, 0, 1, 0)
        assertEquals(expected, count)
    }
    
    @Test
    fun testCountsWithFive() {
        val count = counts(5).toList()
        
        // For n=5, we should get:
        // [0,1,2,3,4] from n=5
        // [0,1,2,3] from n=4
        // [0,1,2] from n=3
        // [0,1] from n=2
        // [0] from n=1
        val expected = listOf(
            0, 1, 2, 3, 4,  // n=5
            0, 1, 2, 3,     // n=4
            0, 1, 2,        // n=3
            0, 1,           // n=2
            0               // n=1
        )
        assertEquals(expected, count)
    }
}