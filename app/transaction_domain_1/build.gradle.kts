plugins {
    id("kotlin-android")
    id("com.android.library")
    id("kotlin-kapt")
}

android {
    namespace = "com.gmail.jiangyang5157.transaction_domain"
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
        sourceCompatibility = Config.JavaTarget
        targetCompatibility = Config.JavaTarget
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
    implementation(Dep.Gson)
    implementation(Dep.CoroutinesCore)

    testImplementation(Dep.Junit)

    androidTestImplementation(Dep.AndroidxJunit)
    androidTestImplementation(Dep.EspressoCore)

    // Internal
    implementation(project(":common"))
    api(project(":transaction_domain_base"))

    // Database
    implementation(Dep.Room)
    implementation(Dep.RoomKtx)
    kapt(Dep.RoomCompiler)
}
