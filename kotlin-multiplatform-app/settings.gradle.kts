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
include(":shared")

include(":kit")
include(":common")
include(":video")
include(":adapter")
include(":widget")

include(":androidApp")
include(":demo-video")
include(":demo-adapter")
include(":demo-compose")
