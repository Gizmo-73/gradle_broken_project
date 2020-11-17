import org.gradle.api.Project
import org.gradle.kotlin.dsl.extra
import java.util.*

fun Project.loadLocalProjectProperties() {
    val localPropertiesFile = file(rootDir.path.plus("/gradle/local.properties"))
    if (!localPropertiesFile.exists()) {
        logger.warn("Local properties file not found. \n" +
                "You can set local project extra properties by putting \n" +
                "it in gradle/local.properties file file in root project directory.")
        return
    }
    logger.warn("Loading of local root project properties from {} started.", localPropertiesFile)
    val localProps = Properties()
    localProps.load(file(localPropertiesFile).inputStream())
    for (propertyKey in localProps.keys()) {
        if (propertyKey !is String) {
            continue
        }
        val propertyValue = localProps[propertyKey]
        logger.warn("Property {} set to {}", propertyKey, propertyValue)
        project.extra[propertyKey] = propertyValue
    }
    logger.warn("Loading of local root project properties finished.")
}