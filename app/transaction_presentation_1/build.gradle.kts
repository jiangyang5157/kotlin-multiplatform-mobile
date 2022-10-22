plugins {
    id("kotlin-android")
    id("com.android.library")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.gmail.jiangyang5157.transaction_presentation"
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = Config.KotlinJvmTarget
    }
}

dependencies {
    implementation(Dep.Kotlin)
    implementation(Dep.CoreKtx)
    implementation(Dep.Appcompat)
    implementation(Dep.Material)

    testImplementation(Dep.Junit)

    androidTestImplementation(Dep.AndroidxJunit)
    androidTestImplementation(Dep.EspressoCore)

    // Internal
    implementation(project(":kit-kt"))
    implementation(project(":common"))
    implementation(project(":adapter"))
    implementation(project(":transaction_domain_1"))
    implementation(project(":transaction_presentation_base"))

    // Dependency Injection
    implementation(Dep.DaggerHilt)
    kapt(Dep.DaggerHiltCompiler)
    kapt(Dep.HiltCompiler)

    // Navigation
    implementation(Dep.NavigationFragmentKtx)
    implementation(Dep.NavigationUiKtx)

    // Lifecycle
    implementation(Dep.LifecycleVmKtx)
    implementation(Dep.LifecycleLiveDataKtx)
}
