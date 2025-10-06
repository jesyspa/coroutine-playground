import kotlin.test.Test

class ScopeFunctionExamplesTest {
    @Test
    fun coreExamples() {
        var a = 5
        var b = 3
        // run with a as parameter, return block result
        var c = a.let { it + b }
        assert(c == 8)
        // run, return block result
        run { a += 1 }
        assert(a == 6)
        // run with b as receiver, return block result
        b.run { a += this }
        assert(a == 9)
        // run with b as receiver, return Unit
        with(b) { b += this }
        assert(b == 6)
        // run with a as parameter, return a
        val d = a.also { c = it + b }
        assert(d == 9)
        // run with b as receiver, return b
        val e = b.apply { c = a + this }
        assert(c == 15)
        assert(e == 6)

        // Ignoring the parameter/receiver distinction, we get two functions:
        // - let: take value as a parameter, return block result
        // - also: take value as a parameter, return value itself
        // note that with is only useful for receiver shenanigans.
    }

    @Test
    fun takeExamples() {
        val a = 5
        val b = a.takeIf { it < 3 }
        assert(b == null)
        val c = a.takeUnless { it < 3 }
        assert(c == a)

        // The two are equivalent, so focusing on takeIf:
        // - result is null if the condition is false
        // - result is the original value if the condition is true
    }

    class Ex(var isClosed: Boolean) : AutoCloseable {
        override fun close() {
            isClosed = true
        }
    }

    @Test
    fun closeExamples() {
        val ex = Ex(false)
        ex.use {
            assert(!it.isClosed)
        }
        assert(ex.isClosed)
    }

    // TDOO: synchronized?

    fun <T> Boolean.ifTrue(block: () -> T) = if (this) block() else null

    @Test
    fun ifTrueExamples() {
        val a = true.ifTrue { 1 }
        assert(a == 1)
        val b = false.ifTrue { 1 }
        assert(b == null)
    }

    class Globals(val a: Int, val b: Boolean)
    fun ifTrueLogicExample(g: Globals): Int {
        // requires acc(g.a, read)
        // requires acc(g.b, read)
        // requires g.b ==> g.a != 0
        return g.b.ifTrue {
            // requires acc(g.b, read)
            // requires acc(g.a, read)
            // requires g.b ==> g.a != 0
            // requires g.b
            // derive: g.a != 0
            100 / g.a
        } ?: 0

        /* Note: this translation into methods does not quite work:
         * method example_lambda(g: Ref) returns (ret: Int)
         *   requires acc(g.a, read)
         *   requires acc(g.b, read)
         *   requires g.b ==> g.a != 0
         *   requires g.b
         * {
         *   ret := 100 / g.a
         * }
         *
         * But even with this, we cannot verify the call site:
         *
         * method ifTrueLogicExample(g: Globals) returns (ret: Int)
         *   requires acc(g.a, read)
         *   requires acc(g.b, read)
         *   requires g.b ==> g.a != 0
         * {
         *   // ifTrue pre-lambda call havoc
         *   if (*) {
         *     ret := example_lambda(g)
         *   }
         *   // ifTrue post-lambda call havoc
         * }
         *
         * ifTrue needs to somehow express that its first argument holds if the lambda is called.
         * In other words, we need something like a "call invariant".
         * Then verifying ifTrue would require verifying that call invariant.
         */
    }

    @Test
    fun ifEmptyExamples() {
        // ifEmpty returns the list itself if it's not empty, otherwise returns the block result.'
        val xs = listOf<Int>()
        val a = xs.ifEmpty { listOf(1) }
        assert(a == listOf(1))
        val b = a.ifEmpty { listOf(2) }
        assert(b == listOf(1))
    }
}
