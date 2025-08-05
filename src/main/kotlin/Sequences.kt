package jesyspa

/* Return a Sequence<Int> containing the 32-bit powers of 2.
 *
 * Open questions:
 * 1. What is the postcondition of this function?
 * 2. What is the invariant on the loop?
 *    - We use repeat here instead of a loop: how much harder is that?
 * 3. Does the yield itself need an annotation?
 */
fun powersOf2() = sequence {
    var i = 1
    repeat(32) {
        yield(i)
        i *= 2
    }
}

// Minor variation: if we do it this way, is it harder? (i outside lambda)
fun powersOf2Capturing(): Sequence<Int> {
    var i = 1
    return sequence {
        repeat(32) {
            yield(i)
            i *= 2
        }
    }
}

/* Returns the sequence 1, 2, 1, 2...
 *
 * Does the fact this is an infinite sequence matter?
 */
fun periodic() = sequence {
    while (true) {
        yield(1)
        yield(2)
    }
}

/* Returns a sequence of counts, long to short.
 *
 * What is the postcondition of countRec?  Is it...
 *   - the same kind of postcondition as on a function like powersOf2?
 *   - the same kind of postcondition as on yield? (if that has one)
 * Can calls to countRec be annotated with invariants somehow?
 */
suspend fun SequenceScope<Int>.countRec(n: Int) {
    if (n <= 0) return
    for (i in 0..<n) {
        yield(i)
    }
    countRec(n - 1)
}

fun counts(n: Int) = sequence { countRec(n) }
