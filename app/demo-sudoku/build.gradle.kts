import com.android.build.api.dsl.ManagedVirtualDevice

plugins {
    id("kotlin-android")
    id("com.android.application")
}

android {
    namespace = "com.gmail.jiangyang5157.demo_sudoku"
    compileSdk = Config.CompileSdkVersion

    defaultConfig {
        minSdk = Config.MinSdkVersion
        targetSdk = Config.TargetSdkVersion
        applicationId = "com.gmail.jiangyang5157.demo_sudoku"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        versionCode = Config.VersionCode
        versionName = Config.VersionName
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = Config.KotlinJvmTarget
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Config.KotlinCompilerExtVersion
    }

    packagingOptions {
        resources {
            excludes += "META-INF/AL2.0"
            excludes += "META-INF/LGPL2.1"
        }
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
    implementation(Dep.Appcompat)
    implementation(Dep.Material)
    implementation(Dep.NavigationCompose)
    implementation(Dep.ComposeMaterial)
    implementation(Dep.ComposeRuntimeLiveData)
    implementation(Dep.ComposeUi)
    implementation(Dep.ComposeUiText)
    implementation(Dep.ComposeUiToolingPreview)
    implementation(Dep.ComposeUiTooling)

    debugImplementation(Dep.ComposeUiTestManifest)

    testImplementation(Dep.Junit)

    androidTestImplementation(Dep.AndroidxJunit)
    androidTestImplementation(Dep.TestRunner)
    androidTestImplementation(Dep.ComposeUiTest)
    androidTestImplementation(Dep.ComposeUiTestJunit)

    // Internal
}
