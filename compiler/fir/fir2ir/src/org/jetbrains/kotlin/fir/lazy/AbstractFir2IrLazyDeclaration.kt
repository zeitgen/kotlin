/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.lazy

import org.jetbrains.kotlin.fir.backend.Fir2IrComponents
import org.jetbrains.kotlin.fir.backend.toIrType
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter
import org.jetbrains.kotlin.fir.symbols.Fir2IrBindableSymbol
import org.jetbrains.kotlin.ir.declarations.*
import org.jetbrains.kotlin.ir.declarations.lazy.lazyVar
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall
import kotlin.properties.ReadWriteProperty

interface AbstractFir2IrLazyDeclaration<F : FirMemberDeclaration, D : IrDeclaration> : IrDeclarationParent, Fir2IrComponents {
    val fir: F

    val symbol: Fir2IrBindableSymbol<*, D>

    var typeParameters: List<IrTypeParameter>
}

internal fun <F : FirMemberDeclaration, D : IrDeclaration> AbstractFir2IrLazyDeclaration<F, D>.prepareTypeParameters() {
    typeParameters = fir.typeParameters.mapIndexedNotNull { index, typeParameter ->
        if (typeParameter !is FirTypeParameter) return@mapIndexedNotNull null
        classifierStorage.getIrTypeParameter(typeParameter, index).apply {
            parent = this@prepareTypeParameters
            if (superTypes.isEmpty()) {
                superTypes = typeParameter.bounds.map { it.toIrType(typeConverter) }
            }
        }
    }
}

internal fun <F : FirMemberDeclaration, D : IrDeclaration> AbstractFir2IrLazyDeclaration<F, D>.createLazyAnnotations():
        ReadWriteProperty<Any?, List<IrConstructorCall>> = lazyVar {
    fir.annotations.mapNotNull {
        callGenerator.convertToIrConstructorCall(it) as? IrConstructorCall
    }
}
