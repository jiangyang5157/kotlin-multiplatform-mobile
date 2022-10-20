pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "kotlin-multiplatform-app"
include(":androidApp")
include(":shared")
include(":kit")
include(":common")
include(":video")
include(":adapter")
include(":demo-video")
include(":demo-adapter")
include(":widget")
