import com.android.build.api.dsl.ManagedVirtualDevice

plugins {
    id("kotlin-android")
    id("com.android.library")
    `maven-publish`
}

afterEvaluate {
    publishing {
        publications {
            repositories {
                maven {
                    url = uri(layout.buildDirectory.dir("maven"))
                }
            }
            val androidSourcesJar = tasks.create<Jar>("sources") {
                archiveClassifier.set("sources")
                from(android.sourceSets["main"].java.srcDirs)
            }
            create<MavenPublication>("maven") {
                groupId = "com.gmail.jiangyang5157"
                artifactId = "adapter"
                version = "1.0"

                from(components["release"])
                artifact(androidSourcesJar)
            }
        }
    }
}

android {
    namespace = "com.gmail.jiangyang5157.adapter"
    compileSdk = Config.CompileSdkVersion

    defaultConfig {
        minSdk = Config.MinSdkVersion
        targetSdk = Config.TargetSdkVersion
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                    getDefaultProguardFile("proguard-android.txt"),
                    file("proguard-rules.pro")
            )
        }
    }

    lint {
        abortOnError = false
        lintConfig = rootProject.file("lint.xml")
        htmlReport = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dep.Kotlin)
    implementation(Dep.Appcompat)
    implementation(Dep.Recycleview)

    testImplementation(Dep.Junit)

    androidTestImplementation(Dep.AndroidxJunit)
    androidTestImplementation(Dep.TestRunner)
}