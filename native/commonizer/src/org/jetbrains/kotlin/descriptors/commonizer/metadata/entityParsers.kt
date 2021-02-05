/*
 * Copyright 2010-2021 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.descriptors.commonizer.metadata

import kotlinx.metadata.KmVariance
import org.jetbrains.kotlin.types.Variance

@Suppress("NOTHING_TO_INLINE")
private inline fun KmVariance.parseVariance(): Variance = when (this) {
    KmVariance.INVARIANT -> Variance.INVARIANT
    KmVariance.IN -> Variance.IN_VARIANCE
    KmVariance.OUT -> Variance.OUT_VARIANCE
}
