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

include(":shared-common") // kmm
include(":shared-puzzle") // kmm
include(":demo-sudoku")

include(":transaction_domain_base") // kotlin library
include(":transaction_domain_1")
include(":transaction_domain_2") // kotlin library
include(":transaction_data_1")
include(":transaction_data_2")
include(":transaction_presentation_base")
include(":transaction_presentation_1")
include(":transaction_presentation_2")
include(":demo-transaction")
