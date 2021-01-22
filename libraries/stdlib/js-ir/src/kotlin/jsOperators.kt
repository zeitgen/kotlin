/*
 * Copyright 2010-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license
 * that can be found in the license/LICENSE.txt file.
 */

@file:Suppress("UNUSED_PARAMETER")

package kotlin.js

// TODO: Implemet as compiler intrinsics

/**
 * Function corresponding to JavaScript's `typeof` operator
 */
public fun jsTypeOf(value: Any?): String =
    js("typeof value").unsafeCast<String>()

internal fun jsDeleteProperty(obj: Any, property: Any) {
    js("delete obj[property]")
}

internal fun jsBitwiseOr(lhs: Any?, rhs: Any?): Int =
    js("lhs | rhs").unsafeCast<Int>()

internal fun jsBitwiseAnd(lhs: Any?, rhs: Any?): Int =
    js("lhs & rhs").unsafeCast<Int>()

internal fun jsInstanceOf(obj: Any?, jsClass: Any?): Boolean =
    js("obj instanceof jsClass").unsafeCast<Boolean>()

// Returns true if the specified property is in the specified object or its prototype chain.
internal fun jsIn(lhs: Any?, rhs: Any): Boolean =
    js("lhs in rhs").unsafeCast<Boolean>()

