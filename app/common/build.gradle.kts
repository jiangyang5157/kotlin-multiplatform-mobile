import com.android.build.api.dsl.ManagedVirtualDevice

plugins {
    id("kotlin-android")
    id("com.android.library")
    `maven-publish`
    kotlin("plugin.serialization") version Version.JetbrainsKotlin
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
                artifactId = "common"
                version = "1.0"

                from(components["release"])
                artifact(androidSourcesJar)
            }
        }
    }
}

android {
    namespace = "com.gmail.jiangyang5157.common"
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

    testOptions {
        managedDevices {
            devices {
                val googleApi31Pixel4 by creating(ManagedVirtualDevice::class) {
                    device = "Pixel 4"
                    apiLevel = 31
                    systemImageSource = "google"
                }
            }
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dep.Kotlin)
    implementation(Dep.Inject)
    implementation(Dep.Appcompat)
    implementation(Dep.Material)
    implementation(Dep.LifecycleRuntime)
    implementation(Dep.CoroutinesAndroid)
    implementation(Dep.KotlinxSerializationJson)

    implementation(platform(Dep.KotlinBom)) // Align versions of all Kotlin components

    testImplementation(Dep.Junit)
    testImplementation(Dep.CoroutinesTest)

    androidTestImplementation(Dep.AndroidxJunit)
    androidTestImplementation(Dep.TestRunner)
    androidTestImplementation(Dep.CoroutinesTest)

    // Internal
    api(project(":kit"))

    // Network
    implementation(Dep.Retrofit)
    implementation(Dep.RetrofitGson)
}
