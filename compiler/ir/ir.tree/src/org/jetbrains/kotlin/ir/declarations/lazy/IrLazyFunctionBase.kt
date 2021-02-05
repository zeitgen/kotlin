/*
 * Copyright 2010-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.ir.declarations.lazy

import org.jetbrains.kotlin.descriptors.FunctionDescriptor
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor
import org.jetbrains.kotlin.ir.declarations.*
import org.jetbrains.kotlin.ir.types.IrType
import kotlin.properties.ReadWriteProperty

interface IrLazyFunctionBase : IrLazyDeclarationBase, IrTypeParametersContainer {
    override val descriptor: FunctionDescriptor

    val initialSignatureFunction: IrFunction?
}

internal fun IrLazyFunctionBase.createInitialSignatureFunction(): Lazy<IrFunction?> =
    lazy(LazyThreadSafetyMode.PUBLICATION) {
        descriptor.initialSignatureDescriptor?.takeIf { it != descriptor }?.original?.let(stubGenerator::generateFunctionStub)
    }

internal fun IrLazyFunctionBase.createValueParameters(): ReadWriteProperty<Any?, List<IrValueParameter>> =
    lazyVar {
        typeTranslator.buildWithScope(this) {
            descriptor.valueParameters.mapTo(arrayListOf()) {
                stubGenerator.generateValueParameterStub(it).apply { parent = this@createValueParameters }
            }
        }
    }

internal fun IrLazyFunctionBase.createReceiverParameter(
    parameter: ReceiverParameterDescriptor?,
    functionDispatchReceiver: Boolean = false,
): ReadWriteProperty<Any?, IrValueParameter?> =
    lazyVar {
        when {
            parameter == null -> null
            functionDispatchReceiver && stubGenerator.extensions.isStaticFunction(descriptor) -> null
            else -> typeTranslator.buildWithScope(this) {
                generateReceiverParameterStub(parameter).also { it.parent = this@createReceiverParameter }
            }
        }
    }

internal fun IrLazyFunctionBase.createReturnType(): ReadWriteProperty<Any?, IrType> =
    lazyVar {
        typeTranslator.buildWithScope(this) {
            typeTranslator.translateType(descriptor.returnType!!)
        }
    }
