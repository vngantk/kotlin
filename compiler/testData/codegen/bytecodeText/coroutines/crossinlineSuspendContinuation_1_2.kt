// WITH_RUNTIME
// LANGUAGE_VERSION: 1.2
// IGNORE_BACKEND: JVM
// WITH_COROUTINES
import helpers.*
import kotlin.coroutines.experimental.*
import kotlin.coroutines.experimental.intrinsics.*

fun builder(c: suspend () -> Unit) {
    c.startCoroutine(EmptyContinuation)
}

interface SuspendRunnable {
    suspend fun run(): String
}

inline fun inlineMe(crossinline c: suspend () -> String) = object : SuspendRunnable {
    override suspend fun run() = c()
}

fun box(): String {
    var res = "FAIL"
    builder {
        res = inlineMe { "OK" }.run()
    }
    return res
}

// Test for continuation of 'run' transformation.
// Since continuation is not suspend lambda, it should not have state-machine.
// @CrossinlineSuspendContinuation_1_2Kt$box$1$doResume$$inlined$inlineMe$1$1.class:
// 0 TABLESWITCH