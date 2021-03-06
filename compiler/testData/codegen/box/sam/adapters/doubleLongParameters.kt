// DONT_TARGET_EXACT_BACKEND: JS JS_IR JS_IR_ES6 WASM NATIVE
// MODULE: lib
// FILE: GenericInterface.java

interface GenericInterface<T> {
    public T foo(double d, int i, long j, short s);
}

// MODULE: main(lib)
// FILE: 1.kt

internal fun getInterface(): GenericInterface<String> {
    return GenericInterface { d, i, j, s ->
        "OK"
    }
}

fun box(): String {
    return getInterface().foo(0.0, 0, 0, 0)
}
