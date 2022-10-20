buildscript {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }

    dependencies {
        classpath(BuildPlugin.Kotlin)
        classpath(BuildPlugin.Android)
        classpath(BuildPlugin.Versions)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }

    apply<com.github.benmanes.gradle.versions.VersionsPlugin>()
}

subprojects {

}

tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
    rejectVersionIf {
        if (Dep.isStableVersion(currentVersion)) {
            Dep.isNotStableVersion(candidate.version)
        } else {
            false
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
