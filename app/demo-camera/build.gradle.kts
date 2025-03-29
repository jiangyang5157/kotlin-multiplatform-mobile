plugins {
    id("kotlin-android")
    id("com.android.application")
}

android {
    namespace = "com.gmail.jiangyang5157.demo_camera"
    compileSdk = Config.CompileSdkVersion

    defaultConfig {
        minSdk = Config.MinSdkVersion
        applicationId = "com.gmail.jiangyang5157.demo_camera"
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
        sourceCompatibility = Config.JavaTarget
        targetCompatibility = Config.JavaTarget
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

    packaging {
        resources {
            excludes += "META-INF/AL2.0"
            excludes += "META-INF/LGPL2.1"
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dep.Kotlin)
    implementation(Dep.Appcompat)
    implementation(Dep.Material)
    implementation(Dep.NavigationCompose)
    implementation(Dep.ComposeMaterial3)
    implementation(Dep.ComposeUi)
    implementation(Dep.ComposeUiText)
    implementation(Dep.ComposeUiToolingPreview)
    implementation(Dep.ComposeUiTooling)
    implementation(Dep.BarcodeScanning)
    implementation(Dep.CameraCore)
    implementation(Dep.CameraView)
    implementation(Dep.CameraLifecycle)
    implementation(Dep.CameraVersion2)

    testImplementation(Dep.Junit)

    androidTestImplementation(Dep.AndroidxJunit)
    androidTestImplementation(Dep.TestRunner)
    androidTestImplementation(Dep.ComposeUiTest)
    androidTestImplementation(Dep.ComposeUiTestJunit)

    // Internal
}