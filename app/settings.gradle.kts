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

include(":kit")
include(":common")

include(":adapter")
include(":demo-adapter")

include(":video")
include(":demo-video")

include(":router")
include(":demo-router")

include(":widget")

include(":demo-compose")

include(":transaction_domain_kt")
include(":transaction_domain")
include(":transaction_data_1")
include(":transaction_data_2")
include(":transaction_presentation_base")
include(":transaction_presentation_1")
include(":transaction_presentation_2")
include(":demo-transaction")

include(":shared")
include(":androidApp")
