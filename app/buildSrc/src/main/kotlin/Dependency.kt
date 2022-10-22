import java.util.*

object Config {

    const val CompileSdkVersion = 33
    const val TargetSdkVersion = 33
    const val MinSdkVersion = 23

    const val VersionCode = 1
    const val VersionName = "1.0"

    const val KotlinJvmTarget = "1.8"
    const val KotlinCompilerExtVersion = "1.3.2"
}

object Version {

    const val JetbrainsKotlin = "1.7.20"
    const val JetbrainsKotlinx = "1.6.4"

    const val AndroidToolsBuild = "7.3.1"
    const val AndroidSupport = "28.0.0"

    const val AndroidxAppcompat = "1.5.1"
    const val AndroidxActivity = "1.5.1"
    const val AndroidxCore = "1.9.0"
    const val AndroidxConstraintlayout = "2.1.4"
    const val AndroidxRecycleview = "1.2.1"
    const val AndroidxHilt = "1.0.0"
    const val AndroidxNavigation = "2.5.2"
    const val AndroidxRoom = "2.4.3"
    const val AndroidxCompose = "1.2.1"
    const val AndroidxArchCore = "2.1.0"
    const val AndroidxLifecycle = "2.5.1"
    const val AndroidxTest = "1.4.0"
    const val AndroidxTestExt = "1.1.3"
    const val AndroidxTestEspresso = "3.4.0"

    const val GoogleGson = "2.9.1"
    const val GoogleMaterial = "1.6.1"
    const val GoogleDagger = "2.44"

    const val SquareUpRetrofit2 = "2.9.0"
    const val SquareUpPicasso = "2.71828"

    const val Junit = "4.13.2"

    const val JavaxInject = "1"

    const val DiffPlugSpotless = "6.11.0"

    const val GithubBenManes = "0.43.0"
}

object BuildPlugin {

    const val Android = "com.android.tools.build:gradle:${Version.AndroidToolsBuild}"

    const val Kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.JetbrainsKotlin}"

    const val Hilt = "com.google.dagger:hilt-android-gradle-plugin:${Version.GoogleDagger}"

    const val NavigationArgs =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Version.AndroidxNavigation}"

    const val Spotless = "com.diffplug.spotless:spotless-plugin-gradle:${Version.DiffPlugSpotless}"

    const val Versions = "com.github.ben-manes:gradle-versions-plugin:${Version.GithubBenManes}"
}

object Dep {

    const val Kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Version.JetbrainsKotlin}"
    const val KotlinTestJunit = "org.jetbrains.kotlin:kotlin-test-junit:${Version.JetbrainsKotlin}"
    const val CoroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.JetbrainsKotlinx}"
    const val CoroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.JetbrainsKotlinx}"
    const val CoroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.JetbrainsKotlinx}"

    const val ComposeUi = "androidx.compose.ui:ui:${Version.AndroidxCompose}"
    const val ComposeUiTooling = "androidx.compose.ui:ui-tooling:${Version.AndroidxCompose}"
    const val ComposeUiToolingPreview =
        "androidx.compose.ui:ui-tooling-preview:${Version.AndroidxCompose}"
    const val ComposeUiTest = "androidx.compose.ui:ui-test:${Version.AndroidxCompose}"
    const val ComposeUiTestJunit = "androidx.compose.ui:ui-test-junit4:${Version.AndroidxCompose}"
    const val ComposeUiTestManifest =
        "androidx.compose.ui:ui-test-manifest:${Version.AndroidxCompose}"
    const val ComposeRuntimeLiveData =
        "androidx.compose.runtime:runtime-livedata:${Version.AndroidxCompose}"
    const val ComposeMaterial = "androidx.compose.material:material:${Version.AndroidxCompose}"
    const val ComposeFoundation =
        "androidx.compose.foundation:foundation:${Version.AndroidxCompose}"

    const val ActivityCompose = "androidx.activity:activity-compose:${Version.AndroidxActivity}"

    const val LifecycleVmKtx =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.AndroidxLifecycle}"
    const val LifecycleLiveDataKtx =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Version.AndroidxLifecycle}"
    const val LifecycleRuntime =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Version.AndroidxLifecycle}"

    const val NavigationFragmentKtx =
        "androidx.navigation:navigation-fragment-ktx:${Version.AndroidxNavigation}"
    const val NavigationUiKtx =
        "androidx.navigation:navigation-ui-ktx:${Version.AndroidxNavigation}"
    const val NavigationCompose =
        "androidx.navigation:navigation-compose:${Version.AndroidxNavigation}"

    const val Room = "androidx.room:room-runtime:${Version.AndroidxRoom}"
    const val RoomKtx = "androidx.room:room-ktx:${Version.AndroidxRoom}"
    const val RoomCompiler = "androidx.room:room-compiler:${Version.AndroidxRoom}"

    const val TestRunner = "androidx.test:runner:${Version.AndroidxTest}"
    const val TestRules = "androidx.test:rules:${Version.AndroidxTest}"

    const val AndroidxJunit = "androidx.test.ext:junit:${Version.AndroidxTestExt}"

    const val EspressoCore = "androidx.test.espresso:espresso-core:${Version.AndroidxTestEspresso}"

    const val CoreKtx = "androidx.core:core-ktx:${Version.AndroidxCore}"

    const val AndroidDesign = "com.android.support:design:${Version.AndroidSupport}"

    const val CoreTest = "androidx.arch.core:core-testing:${Version.AndroidxArchCore}"

    const val Constraintlayout =
        "androidx.constraintlayout:constraintlayout:${Version.AndroidxConstraintlayout}"

    const val Recycleview = "androidx.recyclerview:recyclerview:${Version.AndroidxRecycleview}"

    const val HiltCompiler = "androidx.hilt:hilt-compiler:${Version.AndroidxHilt}"

    const val Appcompat = "androidx.appcompat:appcompat:${Version.AndroidxAppcompat}"

    const val DaggerHilt = "com.google.dagger:hilt-android:${Version.GoogleDagger}"
    const val DaggerHiltCompiler = "com.google.dagger:hilt-android-compiler:${Version.GoogleDagger}"
    const val DaggerHiltTest = "com.google.dagger:hilt-android-testing:${Version.GoogleDagger}"

    const val Material = "com.google.android.material:material:${Version.GoogleMaterial}"

    const val Gson = "com.google.code.gson:gson:${Version.GoogleGson}"

    const val Retrofit = "com.squareup.retrofit2:retrofit:${Version.SquareUpRetrofit2}"
    const val RetrofitGson = "com.squareup.retrofit2:converter-gson:${Version.SquareUpRetrofit2}"

    const val Picasso = "com.squareup.picasso:picasso:${Version.SquareUpPicasso}"

    const val Inject = "javax.inject:javax.inject:${Version.JavaxInject}"

    const val Junit = "junit:junit:${Version.Junit}"

    @JvmStatic
    fun isStableVersion(version: String): Boolean {
        val keywords = listOf("RELEASE", "FINAL", "GA")
        val hasKeyword = keywords.any {
            version.toUpperCase(Locale.ROOT).contains(it)
        }
        val versionRegex = "^[0-9,.v-]+(-r)?$".toRegex()
        val matchesVersionRegex = versionRegex.matches(version)

        return hasKeyword || matchesVersionRegex
    }

    @JvmStatic
    fun isNotStableVersion(version: String): Boolean {
        return !isStableVersion(version)
    }
}