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
include(":androidApp")

include(":kit")
include(":common")

include(":adapter")
include(":demo-adapter")

include(":video")
include(":demo-video")

include(":router")
include(":demo-router")

include(":widget")

include(":transaction_domain")
include(":transaction_data_1")
include(":transaction_data_2")
include(":transaction_presentation_base")
include(":transaction_presentation_1")
include(":transaction_presentation_2")

