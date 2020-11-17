plugins {
    `java-platform`
}

javaPlatform {
    allowDependencies()
}

dependencies {
    api(enforcedPlatform("org.junit:junit-bom:5.6.0"))
    constraints {
//        api("com.zeroc:ice:3.7.3")
//        runtime("com.zeroc:icessl:3.7.3")
    }
}