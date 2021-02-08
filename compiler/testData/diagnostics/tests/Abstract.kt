// !LANGUAGE: +ApproximateContravariantCapturedTypeProperly

class Foo<T : Number>(var x: T) {
    fun setX1(y: T): T {
        this.x = y
        return y
    }
}

fun <T : Number> Foo<T>.setX(): T {
    return this.x
}

class Foo2<T>(var x: T) {
    fun setX1(y: T): T {
        this.x = y
        return y
    }
}

fun <T> Foo2<T>.setX(y: T): T {
    this.x = y
    return y
}

fun Float.bar() {}

fun test1() {
    val fooSetRef = <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.reflect.KFunction2<Foo<*>, kotlin.Nothing, kotlin.Number>")!>Foo<*>::<!TYPE_MISMATCH("")!>setX<!><!>
    val foo = Foo<Float>(1f)

    fooSetRef.invoke(foo, <!CONSTANT_EXPECTED_TYPE_MISMATCH!>1<!>)

    foo.x.bar()
}

fun test2() {
    val fooSetRef = <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.reflect.KFunction2<Foo<*>, kotlin.Nothing, kotlin.Number>")!>Foo<*>::setX1<!>
    val foo = Foo<Float>(1f)

    fooSetRef.invoke(foo, <!CONSTANT_EXPECTED_TYPE_MISMATCH!>1<!>)

    foo.x.bar()
}

fun test3() {
    val fooSetRef = <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.reflect.KFunction2<Foo<*>, kotlin.Nothing, kotlin.Number>")!>Foo2<*>::<!TYPE_MISMATCH("")!>setX<!><!>
    val foo = Foo2<Int>(1)

    fooSetRef.invoke(foo, "")

    foo.x.bar()
}

fun test4() {
    val fooSetRef = <!DEBUG_INFO_EXPRESSION_TYPE("kotlin.reflect.KFunction2<Foo2<*>, kotlin.Nothing, kotlin.Any?>")!>Foo2<*>::setX1<!>
    val foo = Foo2<Int>(1)

    fooSetRef.invoke(foo, <!TYPE_MISMATCH!>""<!>)

    foo.x.<!UNRESOLVED_REFERENCE_WRONG_RECEIVER!>bar<!>()
}
