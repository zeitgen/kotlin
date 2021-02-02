/*
 * Copyright 2010-2021 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

@file:Suppress("FunctionName")

package org.jetbrains.kotlin.gradle

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.api.internal.project.ProjectInternal
import org.gradle.testfixtures.ProjectBuilder
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinMetadataTarget
import org.jetbrains.kotlin.gradle.tooling.BuildKotlinToolingMetadataTask
import org.jetbrains.kotlin.tooling.KotlinToolingMetadata
import org.jetbrains.kotlin.tooling.toJsonString
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BuildKotlinToolingMetadataTest {

    @Test
    fun `empty multiplatform setup`() {
        val project = ProjectBuilder.builder().build()
        project.plugins.apply("kotlin-multiplatform")

        val metadata = project.getKotlinToolingMetadata()

        assertEquals("Gradle", metadata.buildSystem)
        assertEquals(project.gradle.gradleVersion, metadata.buildSystemVersion)
        assertEquals(KotlinMultiplatformPluginWrapper::class.java.canonicalName, metadata.buildPlugin)
        assertEquals(project.getKotlinPluginVersion().toString(), metadata.buildPluginVersion)
        assertEquals(1, metadata.projectTargets.size, "Expected one target (metadata)")
        assertTrue(
            KotlinMetadataTarget::class.java.isAssignableFrom(Class.forName(metadata.projectTargets.single().target)),
            "Expect target to be implement ${KotlinMetadataTarget::class.simpleName}"
        )
        assertEquals(
            KotlinPlatformType.common.name, metadata.projectTargets.single().platformType
        )
        assertTrue(metadata.toJsonString().isNotBlank(), "Expected non blank json representation")
    }

    @Test
    fun `JS JVM Android multiplatform setup`() {
        val project = ProjectBuilder.builder().build() as ProjectInternal
        project.plugins.apply("com.android.application")
        project.plugins.apply("kotlin-multiplatform")

        val android = project.extensions.getByType(BaseExtension::class.java)
        val kotlin = project.extensions.getByType(KotlinMultiplatformExtension::class.java)

        android.compileSdkVersion(28)
        kotlin.android()
        kotlin.jvm()
        kotlin.js {
            nodejs()
            browser()
        }

        project.evaluate()

        val metadata = project.getKotlinToolingMetadata()
        assertEquals(KotlinMultiplatformPluginWrapper::class.java.canonicalName, metadata.buildPlugin)

        assertEquals(
            listOf(KotlinPlatformType.common, KotlinPlatformType.androidJvm, KotlinPlatformType.jvm, KotlinPlatformType.js)
                .map { it.name }.sorted(),
            metadata.projectTargets.map { it.platformType }.sorted()
        )
    }

    private fun Project.getKotlinToolingMetadata(): KotlinToolingMetadata {
        val task = project.tasks.named(BuildKotlinToolingMetadataTask.defaultTaskName, BuildKotlinToolingMetadataTask::class.java).get()
        return task.buildKotlinToolingMetadata()
    }
}
