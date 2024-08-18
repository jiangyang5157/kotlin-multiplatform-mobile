plugins {
    kotlin("jvm")
    `java-library`
    `maven-publish`
}

publishing {
    publications {
        repositories {
            maven {
                url = uri(layout.buildDirectory.dir("maven"))
            }
        }
        create<MavenPublication>("maven") {
            groupId = "com.gmail.jiangyang5157"
            artifactId = "kit"
            version = "1.0"

            from(components["java"])
        }
    }
}

java {
    sourceCompatibility = Config.JavaTarget
    targetCompatibility = Config.JavaTarget
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dep.Kotlin)
    implementation(Dep.Gson)

    testImplementation(Dep.KotlinTestJunit)
}
