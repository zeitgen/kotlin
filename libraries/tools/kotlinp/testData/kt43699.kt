// FILE: SomeClass.kt

typealias SomeClassObserver = (SomeClass) -> Unit

class SomeClass {
    fun a() {}
}

fun SomeClass.extensionFunctionA() {}

// FILE: SomeClassWithGenerics.kt

typealias SomeClassWithGenericsObserver<ValueType> = (SomeClassWithGenerics<ValueType>) -> Unit

class SomeClassWithGenerics<ValueType> {
    fun b() {}
}

fun <ValueType> SomeClassWithGenerics<ValueType>.extensionFunctionB() {}

// FILE: SomeClassWithGenericsTypeAliasesOtherFile.kt

class SomeClassWithGenericsTypeAliasesOtherFile<ValueType> {
    fun c() {}
}

fun <ValueType> SomeClassWithGenericsTypeAliasesOtherFile<ValueType>.extensionFunctionC() {}

// FILE: SomeClassWithGenericsTypeAliasesOtherFile+TypeAliases.kt

typealias SomeClassWithGenericsTypeAliasesOtherFileObserver<ValueType> = (SomeClassWithGenericsTypeAliasesOtherFile<ValueType>) -> Unit
