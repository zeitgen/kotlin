// !LANGUAGE: +ProhibitInnerClassesOfGenericClassExtendingThrowable
// !DIAGNOSTICS: -UNUSED_VARIABLE
// JAVAC_EXPECTED_FILE

class OuterGeneric<T> {
    <!INNER_CLASS_OF_GENERIC_THROWABLE_SUBCLASS!>inner class ErrorInnerExn : Exception()<!>

    inner class InnerA {
        <!INNER_CLASS_OF_GENERIC_THROWABLE_SUBCLASS!>inner class ErrorInnerExn2 : Exception()<!>
    }

    class OkNestedExn : Exception()

    val errorAnonymousObjectExn = <!INNER_CLASS_OF_GENERIC_THROWABLE_SUBCLASS!>object : Exception()<!> {}

    fun foo() {
        <!INNER_CLASS_OF_GENERIC_THROWABLE_SUBCLASS!>class OkLocalExn : Exception()<!>

        val errorAnonymousObjectExn = <!INNER_CLASS_OF_GENERIC_THROWABLE_SUBCLASS!>object : Exception()<!> {}
    }

    fun <X> genericFoo() {
        <!INNER_CLASS_OF_GENERIC_THROWABLE_SUBCLASS!>class OkLocalExn : Exception()<!>

        class LocalGeneric<Y> {
            <!INNER_CLASS_OF_GENERIC_THROWABLE_SUBCLASS!>inner class ErrorInnerExnOfLocalGeneric : Exception()<!>
        }
    }
}

class Outer {
    inner class InnerGeneric<T> {
        <!INNER_CLASS_OF_GENERIC_THROWABLE_SUBCLASS!>inner class ErrorInnerExn : Exception()<!>
    }
}

fun <T> genericFoo() {
    <!INNER_CLASS_OF_GENERIC_THROWABLE_SUBCLASS!>class ErrorLocalExnInGenericFun : Exception()<!>

    val errorkAnonymousObjectExnInGenericFun = <!INNER_CLASS_OF_GENERIC_THROWABLE_SUBCLASS!>object : Exception()<!> {}
}
