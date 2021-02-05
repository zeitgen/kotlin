/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.descriptors.commonizer.cir.factory

import kotlinx.metadata.KmTypeAlias
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor
import org.jetbrains.kotlin.descriptors.DescriptorVisibility
import org.jetbrains.kotlin.descriptors.commonizer.cir.*
import org.jetbrains.kotlin.descriptors.commonizer.cir.impl.CirTypeAliasImpl
import org.jetbrains.kotlin.descriptors.commonizer.metadata.decodeVisibility
import org.jetbrains.kotlin.descriptors.commonizer.utils.compactMap
import org.jetbrains.kotlin.descriptors.commonizer.utils.intern
import org.jetbrains.kotlin.name.Name

object CirTypeAliasFactory {
    fun create(source: TypeAliasDescriptor): CirTypeAlias = create(
        annotations = source.annotations.compactMap(CirAnnotationFactory::create),
        name = source.name.intern(),
        typeParameters = source.declaredTypeParameters.compactMap(CirTypeParameterFactory::create),
        visibility = source.visibility,
        underlyingType = CirTypeFactory.create(source.underlyingType, useAbbreviation = true) as CirClassOrTypeAliasType,
        expandedType = CirTypeFactory.create(source.expandedType, useAbbreviation = false) as CirClassType
    )

    fun create(source: KmTypeAlias): CirTypeAlias = create(
        annotations = emptyList(), // TODO: implement
        name = Name.identifier(source.name).intern(),
        typeParameters = emptyList(), // TODO: implement
        visibility = decodeVisibility(source.flags),
        underlyingType = CirTypeFactory.StandardTypes.ANY, // TODO: implement
        expandedType = CirTypeFactory.StandardTypes.ANY, // TODO: implement
    )

    @Suppress("NOTHING_TO_INLINE")
    inline fun create(
        annotations: List<CirAnnotation>,
        name: Name,
        typeParameters: List<CirTypeParameter>,
        visibility: DescriptorVisibility,
        underlyingType: CirClassOrTypeAliasType,
        expandedType: CirClassType
    ): CirTypeAlias {
        return CirTypeAliasImpl(
            annotations = annotations,
            name = name,
            typeParameters = typeParameters,
            visibility = visibility,
            underlyingType = underlyingType,
            expandedType = expandedType
        )
    }
}
