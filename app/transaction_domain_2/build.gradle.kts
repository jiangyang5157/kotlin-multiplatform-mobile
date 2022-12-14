plugins {
    kotlin("jvm")
    `java-library`
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
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