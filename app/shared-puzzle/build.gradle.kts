plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version Version.JetbrainsKotlin
    id("com.android.library")
}

kotlin {
    android()

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = Config.KotlinJvmTarget
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared-puzzle"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Dep.KotlinxSerializationJson)
                implementation(project(":shared-common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "com.gmail.jiangyang5157.shared.puzzle"
    compileSdk = Config.CompileSdkVersion
    defaultConfig {
        minSdk = Config.MinSdkVersion
    }
}