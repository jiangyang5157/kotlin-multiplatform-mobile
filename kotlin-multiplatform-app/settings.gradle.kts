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
include(":adapter")
include(":video")
include(":router")
include(":widget")

include(":androidApp")
include(":demo-adapter")
include(":demo-video")
include(":demo-router")
include(":demo-compose")
