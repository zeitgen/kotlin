/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package kotlin.script.experimental.intellij

import com.intellij.openapi.extensions.ExtensionPointName
import com.intellij.openapi.extensions.Extensions
import com.intellij.psi.PsiFile

fun reloadScriptConfiguration(scriptFile: PsiFile, updateEditorWithoutNotification: Boolean = false) {
    val extensions = Extensions.getArea(scriptFile.project).getExtensionPoint(IdeScriptConfigurationControlFacade.EP_NAME).extensions
    for (extension in extensions) {
        extension.reloadScriptConfiguration(scriptFile, updateEditorWithoutNotification)
    }
}

interface IdeScriptConfigurationControlFacade {

    fun reloadScriptConfiguration(scriptFile: PsiFile, updateEditorWithoutNotification: Boolean = false)

    companion object {
        val EP_NAME: ExtensionPointName<IdeScriptConfigurationControlFacade> =
            ExtensionPointName.create("org.jetbrains.kotlin.ideScriptConfigurationControlFacade")
    }
}
