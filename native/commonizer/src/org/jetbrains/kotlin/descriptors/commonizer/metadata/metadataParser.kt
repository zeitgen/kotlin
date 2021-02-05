/*
 * Copyright 2010-2021 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.descriptors.commonizer.metadata

import kotlinx.metadata.*
import kotlinx.metadata.klib.KlibModuleMetadata
import kotlinx.metadata.klib.fqName
import org.jetbrains.kotlin.descriptors.commonizer.CommonizerParameters
import org.jetbrains.kotlin.descriptors.commonizer.LeafTarget
import org.jetbrains.kotlin.descriptors.commonizer.ModulesProvider.ModuleInfo
import org.jetbrains.kotlin.descriptors.commonizer.TargetProvider
import org.jetbrains.kotlin.descriptors.commonizer.cir.factory.*
import org.jetbrains.kotlin.descriptors.commonizer.mergedtree.*
import org.jetbrains.kotlin.descriptors.commonizer.metadata.utils.SerializedMetadataLibraryProvider
import org.jetbrains.kotlin.descriptors.commonizer.utils.*
import org.jetbrains.kotlin.descriptors.commonizer.utils.intern
import org.jetbrains.kotlin.descriptors.commonizer.utils.internedClassId
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.storage.StorageManager

class CirTreeMergerV2(
    private val storageManager: StorageManager,
    private val classifiers: CirKnownClassifiers,
    private val parameters: CommonizerParameters
) {
    class CirTreeMergeResultV2(
        val root: CirRootNode,
        val missingModuleInfos: Map<LeafTarget, Collection<ModuleInfo>>
    )

    private val leafTargetsSize = parameters.targetProviders.size

    fun merge(): CirTreeMergeResultV2 {
        val result = processRoot()
        System.gc()
        return result
    }

    private fun processRoot(): CirTreeMergeResultV2 {
        val rootNode: CirRootNode = buildRootNode(storageManager, leafTargetsSize)

        // remember any exported forward declarations from common fragments of dependee modules
        parameters.dependeeModulesProvider?.loadModuleInfos()?.forEach(::processCInteropModuleAttributes)

        val commonModuleNames = parameters.getCommonModuleNames()
        val missingModuleInfosByTargets = mutableMapOf<LeafTarget, Collection<ModuleInfo>>()

        parameters.targetProviders.forEachIndexed { targetIndex, targetProvider ->
            val allModuleInfos = targetProvider.modulesProvider.loadModuleInfos()

            val (commonModuleInfos, missingModuleInfos) = allModuleInfos.partition { it.name in commonModuleNames }
            processTarget(rootNode, targetIndex, targetProvider, commonModuleInfos)

            missingModuleInfosByTargets[targetProvider.target] = missingModuleInfos

            parameters.progressLogger?.invoke("Loaded declarations for ${targetProvider.target.prettyName}")
            System.gc()
        }

        return CirTreeMergeResultV2(
            root = rootNode,
            missingModuleInfos = missingModuleInfosByTargets
        )
    }

    private fun processTarget(
        rootNode: CirRootNode,
        targetIndex: Int,
        targetProvider: TargetProvider,
        commonModuleInfos: Collection<ModuleInfo>
    ) {
        rootNode.targetDeclarations[targetIndex] = CirRootFactory.create(targetProvider.target)

        commonModuleInfos.forEach { moduleInfo ->
            val metadata = targetProvider.modulesProvider.loadModuleMetadata(moduleInfo.name)
            val module = KlibModuleMetadata.read(SerializedMetadataLibraryProvider(metadata))
            processModule(rootNode, targetIndex, moduleInfo, module)
        }
    }

    private fun processModule(
        rootNode: CirRootNode,
        targetIndex: Int,
        moduleInfo: ModuleInfo,
        module: KlibModuleMetadata
    ) {
        processCInteropModuleAttributes(moduleInfo)

        val moduleName: Name = Name.special(module.name).intern()
        val moduleNode: CirModuleNode = rootNode.modules.getOrPut(moduleName) {
            buildModuleNode(storageManager, leafTargetsSize)
        }
        moduleNode.targetDeclarations[targetIndex] = CirModuleFactory.create(moduleName)

        val groupedFragments: Map<FqName, Collection<KmModuleFragment>> = module.fragments.foldToMap { fragment ->
            fragment.fqName?.let(::FqName) ?: error("A fragment without FQ name in module $moduleName: $fragment")
        }

        groupedFragments.forEach { (packageFqName, fragments) ->
            processFragments(moduleNode, targetIndex, fragments, packageFqName.intern())
        }
    }

    private fun processFragments(
        moduleNode: CirModuleNode,
        targetIndex: Int,
        fragments: Collection<KmModuleFragment>,
        packageFqName: FqName
    ) {
        val packageNode: CirPackageNode = moduleNode.packages.getOrPut(packageFqName) {
            buildPackageNode(storageManager, leafTargetsSize)
        }
        packageNode.targetDeclarations[targetIndex] = CirPackageFactory.create(packageFqName)

        val classesToProcess = ClassesToProcess()
        fragments.forEach { fragment ->
            classesToProcess.addClasses(fragment.classes)

            fragment.pkg?.let { pkg ->
                pkg.properties.forEach { property -> processProperty(packageNode, targetIndex, property) }
                pkg.functions.forEach { function -> processFunction(packageNode, targetIndex, function) }
                pkg.typeAliases.forEach { typeAlias -> processTypeAlias(packageNode, targetIndex, typeAlias) }
            }
        }

        classesToProcess.getClassesForScope(FqName.ROOT).forEach { clazz ->
            processClass(packageNode, targetIndex, clazz, classesToProcess)
        }
    }

    private fun processProperty(
        ownerNode: CirNodeWithMembers<*, *>,
        targetIndex: Int,
        property: KmProperty
    ) {
        if (property.isFakeOverride())
            return

        val maybeClassOwnerNode: CirClassNode? = ownerNode as? CirClassNode
        val propertyNode: CirPropertyNode = ownerNode.properties.getOrPut(PropertyApproximationKey(property)) {
            buildPropertyNode(storageManager, leafTargetsSize, classifiers, maybeClassOwnerNode?.commonDeclaration)
        }
        propertyNode.targetDeclarations[targetIndex] = CirPropertyFactory.create(
            source = property,
            containingClass = maybeClassOwnerNode?.targetDeclarations?.get(targetIndex)
        )
    }

    private fun processFunction(
        ownerNode: CirNodeWithMembers<*, *>,
        targetIndex: Int,
        function: KmFunction
    ) {
        if (function.isFakeOverride()
            || function.isKniBridgeFunction()
            || function.isTopLevelDeprecatedFunction(isTopLevel = ownerNode is CirClassNode)
        ) {
            return
        }

        val maybeClassOwnerNode: CirClassNode? = ownerNode as? CirClassNode
        val functionNode: CirFunctionNode = ownerNode.functions.getOrPut(FunctionApproximationKey(function)) {
            buildFunctionNode(storageManager, leafTargetsSize, classifiers, maybeClassOwnerNode?.commonDeclaration)
        }
        functionNode.targetDeclarations[targetIndex] = CirFunctionFactory.create(
            source = function,
            containingClass = maybeClassOwnerNode?.targetDeclarations?.get(targetIndex)
        )
    }

    private fun processClass(
        ownerNode: CirNodeWithMembers<*, *>,
        targetIndex: Int,
        clazz: KmClass,
        classesToProcess: ClassesToProcess
    ) {
        val classId = internedClassId(clazz.name)
        val className = classId.shortClassName.intern()

        val maybeClassOwnerNode: CirClassNode? = ownerNode as? CirClassNode
        val classNode: CirClassNode = ownerNode.classes.getOrPut(className) {
            buildClassNode(storageManager, leafTargetsSize, classifiers, maybeClassOwnerNode?.commonDeclaration, classId)
        }
        classNode.targetDeclarations[targetIndex] = CirClassFactory.create(className, clazz)

        if (!Flag.Class.IS_ENUM_ENTRY(clazz.flags)) {
            clazz.constructors.forEach { constructor -> processClassConstructor(classNode, targetIndex, constructor) }
        }

        clazz.properties.forEach { property -> processProperty(classNode, targetIndex, property) }
        clazz.functions.forEach { function -> processFunction(classNode, targetIndex, function) }

        classesToProcess.getClassesForScope(classId.relativeClassName).forEach { nestedClass ->
            processClass(classNode, targetIndex, nestedClass, classesToProcess)
        }
    }

    private fun processClassConstructor(
        classNode: CirClassNode,
        targetIndex: Int,
        constructor: KmConstructor
    ) {
        val constructorNode: CirClassConstructorNode = classNode.constructors.getOrPut(ConstructorApproximationKey(constructor)) {
            buildClassConstructorNode(storageManager, leafTargetsSize, classifiers, classNode.commonDeclaration)
        }
        constructorNode.targetDeclarations[targetIndex] = CirClassConstructorFactory.create(
            source = constructor,
            containingClass = classNode.targetDeclarations[targetIndex]!!
        )
    }

    private fun processTypeAlias(
        packageNode: CirPackageNode,
        targetIndex: Int,
        typeAliasMetadata: KmTypeAlias
    ) {
        val typeAliasName = Name.identifier(typeAliasMetadata.name).intern()
        val typeAliasClassId = internedClassId(packageNode.packageFqName, typeAliasName)

        val typeAliasNode: CirTypeAliasNode = packageNode.typeAliases.getOrPut(typeAliasName) {
            buildTypeAliasNode(storageManager, leafTargetsSize, classifiers, typeAliasClassId)
        }
        typeAliasNode.targetDeclarations[targetIndex] = CirTypeAliasFactory.create(typeAliasMetadata)
    }

    private fun processCInteropModuleAttributes(moduleInfo: ModuleInfo) {
        val cInteropAttributes = moduleInfo.cInteropAttributes ?: return
        val exportForwardDeclarations = cInteropAttributes.exportForwardDeclarations.takeIf { it.isNotEmpty() } ?: return
        val mainPackageFqName = FqName(cInteropAttributes.mainPackageFqName).intern()

        exportForwardDeclarations.forEach { classFqName ->
            // Class has synthetic package FQ name (cnames/objcnames). Need to transfer it to the main package.
            val className = Name.identifier(classFqName.substringAfterLast('.')).intern()
            classifiers.forwardDeclarations.addExportedForwardDeclaration(internedClassId(mainPackageFqName, className))
        }
    }
}

private class ClassesToProcess {
    private val groupedByScopes = mutableMapOf<FqName, MutableCollection<KmClass>>()

    fun addClasses(classes: Collection<KmClass>) {
        classes.forEach { clazz ->
            val relativeFqName = FqName(clazz.name.substringAfterLast('/')).intern()
            val scopeFqName = relativeFqName.parent().intern()
            groupedByScopes.getOrPut(scopeFqName) { ArrayList() } += clazz
        }
    }

    fun getClassesForScope(scopeFqName: FqName): Collection<KmClass> {
        return groupedByScopes[scopeFqName] ?: emptyList()
    }
}
