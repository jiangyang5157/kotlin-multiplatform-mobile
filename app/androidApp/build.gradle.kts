plugins {
    kotlin("android")
    id("com.android.application")
}

android {
    namespace = "com.gmail.jiangyang5157.kma.android"
    compileSdk = Config.CompileSdkVersion

    defaultConfig {
        applicationId = "com.gmail.jiangyang5157.kma.android"
        minSdk = Config.MinSdkVersion
        targetSdk = Config.TargetSdkVersion
        versionCode = Config.VersionCode
        versionName = Config.VersionName
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Config.KotlinCompilerExtVersion
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(Dep.ComposeUi)
    implementation(Dep.ComposeUiTooling)
    implementation(Dep.ComposeUiToolingPreview)
    implementation(Dep.ComposeFoundation)
    implementation(Dep.ComposeMaterial)
    implementation(Dep.ActivityCompose)
}