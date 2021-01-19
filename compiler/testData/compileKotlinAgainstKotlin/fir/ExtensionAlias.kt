// TARGET_BACKEND: JVM
// FILE: A.kt

typealias ProcessOverriddenWithBaseScope<D> = String.(D, (D, String) -> Boolean) -> Boolean

// FILE: B.kt

private data class NumberWithString<N : Number>(val n: N, val s: String)

private fun <N : Number> use(ns: NumberWithString<N>, process: ProcessOverriddenWithBaseScope<N>): String {
    val (n, s) = ns
    val result = s.process(n) { _, s ->
        s == "OK"
    }
    return if (result) "OK" else "FAIL"
}

fun box(): String {
    return use(NumberWithString(42, "OK")) { n, process ->
        process(n, "OK")
    }
}