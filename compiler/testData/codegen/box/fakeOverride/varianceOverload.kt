
// MODULE: lib
// FILE: lib.kt
// KT-43831

class Change : IsChange {
    override fun filterWithSelect(select: RootPropRefGraph<out PropertyDefinitions>): Change? {
        return null
    }

    fun test(): String = "OK"

}

interface IsChange {
    fun filterWithSelect(select: RootPropRefGraph<out PropertyDefinitions>): IsChange?
}

abstract class PropertyDefinitions : AbstractPropertyDefinitions<Any>()

interface IsPropertyDefinitions {
}


abstract class AbstractPropertyDefinitions<DO : Any> :
    IsPropertyDefinitions {}

class RootPropRefGraph<P : IsPropertyDefinitions>

// MODULE: main(lib)
// FILE: main.kt

fun box(): String {
    return Change().test()
}