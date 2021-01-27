// TARGET_BACKEND: JVM
// WITH_RUNTIME
// MODULE: api
// FILE: IrOverridingUtil_api.kt

abstract class IrSimpleFunction : IrFunction(), IrDeclarationWithModality

abstract class IrFunction : IrDeclarationBase(), IrDeclarationWithVisibility

abstract class IrDeclarationBase : IrDeclaration

interface IrDeclarationWithVisibility {
    var visibility: String
}

interface IrDeclarationWithModality {
    val modality: String
}

interface IrDeclaration

interface IrFakeOverrideFunction {
    var modality: String
}

class Util {
    fun IrSimpleFunction.foo(): String {
        require (this is IrFakeOverrideFunction) {
            "Incorrect function $this"
        }
        if (this.visibility == "PRIVATE") return "OK"

        this.visibility = "PROTECTED"
        this.modality = "OPEN"
        return "FAIL"
    }
}

// MODULE: impl(api)
// FILE: IrOverridingUtil_impl.kt

class IrSimpleFunctionImpl : IrSimpleFunction() {
    override var visibility: String
        get() = "PUBLIC"
        set(_) {}

    override val modality: String
        get() = "FINAL"
}

class IrFakeOverrideFunctionImpl(
    override var visibility: String,
    override var modality: String
) : IrSimpleFunction(), IrFakeOverrideFunction

fun Util.bar(): String {
    return IrFakeOverrideFunctionImpl("PRIVATE", "FINAL").foo()
}

fun box(): String {
    return Util().bar()
}

