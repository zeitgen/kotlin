/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.js.config

import java.util.*

enum class DceMode {
    REMOVAL_DECLARATION,
    LOGGING,
    THROWING_EXCEPTION;

    companion object {
        val DEFAULT = REMOVAL_DECLARATION

        fun resolvePolicy(key: String): DceMode {
            return when (key.toLowerCase(Locale.US)) {
                "removal-declaration" -> REMOVAL_DECLARATION
                "logging" -> LOGGING
                "throwing-exception" -> THROWING_EXCEPTION
                else -> error("Unknown DCE mode '$key'")
            }
        }
    }
}

fun DceMode.isNotRemoving(): Boolean {
    return this != DceMode.REMOVAL_DECLARATION
}

fun DceMode.dceModeToArgumentOfUnreachableMethod(): Int {
    return when (this) {
        DceMode.LOGGING -> 0
        DceMode.THROWING_EXCEPTION -> 1
        DceMode.REMOVAL_DECLARATION -> error("Only logging and throwing exception allowed for unreachable method")
    }
}
