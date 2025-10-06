import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/* Demonstration of some contract-related features.
@OptIn(ExperimentalContracts::class)
inline fun myRun(block: () -> Unit) {
    contract {
        callsInPlace(block, kotlin.contracts.InvocationKind.EXACTLY_ONCE)
    }
    return
    block()
}

fun test(): Int {
    var a = 5
    myRun {
        invariant { ... }
        return@test 3
    }
    return a
    // postcondition
}
 */