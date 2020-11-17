import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.plugins.JavaPlugin
import org.gradle.kotlin.dsl.hasPlugin
import org.gradle.kotlin.dsl.named
import java.util.stream.Collectors

fun attachIfJavaProject(attachTo: (Any) -> Dependency?, projectDependency: ProjectDependency) {
    if (hasJavaConfiguration(projectDependency)) {
        attachTo(projectDependency)
    }
}

fun hasJavaConfiguration(pd: ProjectDependency): Boolean {
    if (pd.dependencyProject.plugins.hasPlugin(JavaPlugin::class)) {
        return true;
    }
    return false;
}

fun Project.getClassPathForJarString(): String =
        configurations.named<org.gradle.api.artifacts.Configuration>("runtimeClasspath")
                .get()
                .resolve()
                .stream()
                .map { it.name }
                .collect(Collectors.joining(" "))
