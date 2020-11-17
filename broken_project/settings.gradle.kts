pluginManagement {
    @Suppress("UnstableApiUsage")
    plugins {
        id("com.github.ManifestClasspath") version "0.1.0-RELEASE"
        id("com.github.hauner.jarTest") version "1.0.1"
        id("com.zeroc.gradle.ice-builder.slice") version "1.4.7"
        id("com.github.johnrengelman.shadow") version "5.2.0"
        id("com.gorylenko.gradle-git-properties") version "2.2.2"
        id("com.google.protobuf") version "0.8.11"
        id("io.qameta.allure") version "2.8.1"
        id("org.openjfx.javafxplugin") version "0.0.8"
        id("me.champeau.gradle.jmh") version "0.5.0"
        id("com.bmuschko.docker-remote-api") version "6.6.0"
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.version != null) {
                throw GradleException("Version of plugin ${requested.id}/${requested.module} must be specified in settings.gradle.kts")
            }
        }
    }
//    repositories {
//        maven {
//            url = uri("../maven-repo")
//        }
//        gradlePluginPortal()
//        ivy {
//            url = uri("../ivy-repo")
//        }
//    }
}

rootProject.name = "broken"

include("platform_module")
project(":platform_module").projectDir = file("platform_module")
