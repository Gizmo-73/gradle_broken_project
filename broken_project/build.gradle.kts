import kotlin.streams.toList

plugins {
    java
    jacoco
}

// Подгружаем локальный настройки проекта
loadLocalProjectProperties()

allprojects {
    group = "com.gmware"
    repositories {
        mavenCentral()
        jcenter()
    }
    buildDir = file("${rootProject.rootDir}/gradleBuild/${getProjectOutDir(project)}_build_dir")
}

configure(subprojects.filter(this::isJavaProject)) {
    apply(plugin = "java")
    apply(plugin = "jacoco")
    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.isWarnings = false
        options.isDeprecation = false
        options.debugOptions.debugLevel = "source,lines,vars"
        options.compilerArgs = mutableListOf("-Xlint:-unchecked", "-Xlint:-deprecation", "-XDsuppressNotes")
        // дополнительные параметры, которые полезно включать во время рефакторинга, для анализа числа
        // ошибок компиляции, для выбора наименее инвазивного спосоа рефакторинга
        // options.compilerArgs.addAll(listOf("-Xmaxerrs", "10000"));
    }
    if (!isConventionalJavaProject(this)) {
        // для проектов, у которых расположение исходников имеет старый формат расположения
        // dir/src и dir/src_test
        sourceSets.main {
            java.setSrcDirs(listOf("src"))
            resources.setSrcDirs(listOf("resources"))
        }
        sourceSets.test {
            java.setSrcDirs(listOf("src_test"))
            resources.setSrcDirs(mutableListOf("resources_test"))
        }
    }
    tasks.test {
        useJUnitPlatform()
        maxHeapSize = "2G"
        setTestWorkDirFromRootProjectProperty("broken_test_work_dir")
    }
    dependencies {
        // подключаю апишки и движки для тестов, а так же фреймворки моков и матчеров
        implementation(platform(project(":platform_module")))
        implementation("org.slf4j:slf4j-api:1.7.26")
        compileOnly("org.jetbrains:annotations:18.0.0")
        testCompileOnly("org.jetbrains:annotations:18.0.0")
        testImplementation(platform(project(":platform_module")))
        testImplementation("org.junit.jupiter:junit-jupiter")
        testImplementation("org.mockito:mockito-inline:3.+") // вместо mockito-core чтобы делать моки файнал-классов
        testImplementation("org.hamcrest:hamcrest:2.1")
        testImplementation("ch.qos.logback:logback-classic:1.2.3")
    }

}

fun getProjectOutDir(p: Project): String {
    if (p.parent == null) {
        return p.name
    }
    return "${getProjectOutDir(p.parent!!)}/${p.name}"
}

fun isJavaProject(p: Project): Boolean = file("${p.projectDir}/src").exists() || file("${p.projectDir}/src_test").exists()

fun isConventionalJavaProject(p: Project): Boolean =
        file("${p.projectDir}/src/main/java").exists() || file("${p.projectDir}/src/test/java").exists()

// libraries versions
val jacksonVersion by extra { "2.11.2" }
val postgresqlDriverVersion by extra { "42.1.1" }
val influxdbVersion by extra { "2.20" }
val nettyVersion by extra { "4.1.51.Final" }
val springBootVersion by extra { "2.3.3.RELEASE" }
