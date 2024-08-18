plugins {
    kotlin("jvm")
    `java-library`
}

java {
    sourceCompatibility = Config.JavaTarget
    targetCompatibility = Config.JavaTarget
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dep.Kotlin)
    implementation(Dep.CoroutinesCore)

    testImplementation(Dep.KotlinTestJunit)

    // Internal
    implementation(project(":kit"))
    implementation(project(":transaction_domain_base"))
}