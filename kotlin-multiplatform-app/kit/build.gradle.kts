plugins {
    id("java-library")
    id("kotlin")
}

java {
    sourceCompatibility = Config.JavaVersion
    targetCompatibility = Config.JavaVersion
}

dependencies {
    implementation(Dep.Kotlin)
    implementation(Dep.Gson)

    testImplementation(Dep.KotlinTestJunit)
}
