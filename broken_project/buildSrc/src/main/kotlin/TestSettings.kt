import org.gradle.api.InvalidUserDataException
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.extra

fun Test.setTestWorkDirFromRootProjectProperty(propertyName: String) {
    if (project.rootProject.hasProperty(propertyName)) {
        val workingDirFile = project.file(project.rootProject.extra[propertyName]!!)
        if (!workingDirFile.exists()) {
            throw InvalidUserDataException("Test working dir ${workingDirFile.absolutePath} doesn't exsists.")
        }
        if (!workingDirFile.isDirectory) {
            throw InvalidUserDataException("Test working dir ${workingDirFile.absolutePath} is not directory.")
        }
        workingDir = workingDirFile
    } else {
        doFirst {
            logger.error("$propertyName project property are not set to execute $name task. \n" +
                    "You can set it in gradle/local.properties file in root project directory.\n" +
                    "You can also set it in .gradle/gradle.properties file in user home directory." +
                    "Or you can set it with -P$propertyName=... command line parameter.")
            throw InvalidUserDataException("$propertyName project property are not set to execute $name task.")
        }
    }
}