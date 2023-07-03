pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

rootProject.name = "app"

include(":kit") // kotlin library

include(":common")

include(":adapter")
include(":demo-adapter")

include(":video")
include(":demo-video")

include(":router")
include(":demo-router")

include(":widget")

include(":demo-compose")
include(":demo-compose-canvas")

include(":transaction_domain_base") // kotlin library
include(":transaction_domain_1")
include(":transaction_domain_2") // kotlin library
include(":transaction_data_1")
include(":transaction_data_2")
include(":transaction_presentation_base")
include(":transaction_presentation_1")
include(":transaction_presentation_2")
include(":demo-transaction")

include(":shared-common")
include(":shared-puzzle")
