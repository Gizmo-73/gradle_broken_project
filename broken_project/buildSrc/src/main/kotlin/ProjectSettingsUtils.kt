import org.gradle.api.initialization.Settings

import java.io.File

/**
 * Adds subproject by directory structure. Names of top projects added to names of subprojects as prefixes, separated by
 * colons.
 * If a directory contains file named as ".not_a_project" then this directory won't be added as subproject.
 * If a directory contains file named as ".has_subprojects" then subdirectories of this directory will be added
 * as subprojects.
 *
 * @param root - root directory, where subprojects retains
 * @param moduleNamePrefix - projectNamePrefix, added to subproject names. Prefix separated by a colon.
 * @param skipRootModuleNameAdditionAsPrefix -
 */
fun Settings.attachModulesByDirectoryStructure(root: File, moduleNamePrefix: String, skipRootModuleNameAdditionAsPrefix: Boolean = false) {
    if (!root.isDirectory) {
        throw java.lang.IllegalArgumentException("root must be a directory.")
    }
    if (!root.exists()) {
        throw IllegalStateException("root directory must exist.")
    }
    val projectName = when {
        moduleNamePrefix.isBlank() -> root.name
        else -> "$moduleNamePrefix:${root.name}"
    }
    if (!root.resolve(".not_a_project").exists()) {
        include(projectName)
        project(":$projectName").projectDir = root
    }
    if (!root.resolve(".has_subprojects").exists()) {
        return
    }
    val newProjectPrefix = when {
        skipRootModuleNameAdditionAsPrefix -> moduleNamePrefix
        else -> projectName
    }
    root.listFiles()?.filter { it.isDirectory && !it.name.startsWith(".") }?.forEach {
        attachModulesByDirectoryStructure(it, newProjectPrefix)
    }
}
