// DONT_TARGET_EXACT_BACKEND: JS JS_IR JS_IR_ES6 WASM NATIVE
// MODULE: lib
// FILE: J.java

public class J<T> {
    protected static String protectedFun() {
        return "OK";
    }
}

// MODULE: main(lib)
// FILE: 1.kt

class Derived : J<String>() {
    fun test(): String {
        return J.protectedFun()!!
    }
}

fun box(): String {
    return Derived().test()
}
