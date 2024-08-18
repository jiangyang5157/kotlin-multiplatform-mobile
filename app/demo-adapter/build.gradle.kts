plugins {
    id("kotlin-android")
    id("com.android.application")
}

android {
    namespace = "com.gmail.jiangyang5157.demo_adapter"
    compileSdk = Config.CompileSdkVersion

    defaultConfig {
        minSdk = Config.MinSdkVersion
        applicationId = "com.gmail.jiangyang5157.demo_adapter"
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
        viewBinding = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dep.Kotlin)
    implementation(Dep.Appcompat)
    implementation(Dep.Material)
    implementation(Dep.Constraintlayout)
    implementation(Dep.Recycleview)

    testImplementation(Dep.Junit)

    androidTestImplementation(Dep.AndroidxJunit)
    androidTestImplementation(Dep.TestRunner)

    // Internal
    implementation(project(":adapter"))
}
