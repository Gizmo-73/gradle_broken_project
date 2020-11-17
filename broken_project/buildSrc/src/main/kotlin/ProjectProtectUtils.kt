import org.gradle.api.Project
import java.io.BufferedReader

fun Project.protectAppJarWithBisGuard(appJar: String) {
    val pb = ProcessBuilder("java", "-jar", "${rootProject.rootDir}/jar/com.bisguard.java.antidecompiler_9.3.jar", "-source", appJar, "-")
    val process = pb.start()
    process.waitFor()
    val exitValue = process.exitValue()
    if (exitValue != 0) {
        val out = process.inputStream.bufferedReader(Charsets.UTF_8).use(BufferedReader::readText)
        logger.error("protectAppJarWithBisGuard: exitValue={}; out={}", exitValue, out)
        throw RuntimeException("Can't protect jar.")
    }
}