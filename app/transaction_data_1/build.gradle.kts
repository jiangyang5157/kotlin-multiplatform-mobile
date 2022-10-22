import com.android.build.api.dsl.ManagedVirtualDevice

plugins {
    id("kotlin-android")
    id("com.android.library")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.gmail.jiangyang5157.transaction_data"
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
    implementation(Dep.Kotlin)
    implementation(Dep.CoreKtx)
    implementation(Dep.Appcompat)
    implementation(Dep.Material)
    implementation(Dep.CoroutinesAndroid)

    testImplementation(Dep.Junit)

    androidTestImplementation(Dep.AndroidxJunit)
    androidTestImplementation(Dep.EspressoCore)
    androidTestImplementation(Dep.CoreTest)
    androidTestImplementation(Dep.CoroutinesTest)

    // Internal
    implementation(project(":kit-kt"))
    implementation(project(":common"))
    implementation(project(":transaction_domain_1"))

    // Network
    implementation(Dep.Retrofit)
    implementation(Dep.RetrofitGson)

    // Database
    api(Dep.Room)
    implementation(Dep.RoomKtx)
    kapt(Dep.RoomCompiler)

    // Dependency Injection
    implementation(Dep.DaggerHilt)
    kapt(Dep.DaggerHiltCompiler)
    kapt(Dep.HiltCompiler)
}
