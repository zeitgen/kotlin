// TARGET_BACKEND: JVM
// FILE: A.kt
// WITH_RUNTIME

class IrBuiltIns {
    object OperatorNames {
        const val LESS = "OK"
    }
}

// FILE: B.kt

fun foo(s: String) = s

fun box(): String {
    return foo(IrBuiltIns.OperatorNames.LESS)
}
