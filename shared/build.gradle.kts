plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.dokka.library)
    alias(libs.plugins.sqldelight.library)
    alias(libs.plugins.kotlinxSerialization)
    id("convention.publication")
    id("io.realm.kotlin") version "2.0.0"
}

group = "my.company.name"
version = "1.0"

kotlin {
    jvmToolchain(17)
    androidTarget {
        publishLibraryVariants("release")
    }

    jvm()

    /*js {
        browser {
            webpackTask {
                mainOutputFileName = "shared.js"
            }
        }
        binaries.executable()
    }*/

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    /*mingwX64 {
        binaries.staticLib {
            baseName = "shared"
        }
    }*/

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kermit)
            implementation(libs.ktor.core)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.koin.core)
            implementation("co.touchlab:stately-common:2.0.5")
            implementation("io.realm.kotlin:library-base:2.0.0")
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation("io.ktor:ktor-client-mock:3.0.0-beta-2")
            implementation("io.insert-koin:koin-test:3.2.0")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
            implementation("co.touchlab:stately-common:2.0.5") //needed for iosSimulatorArm64, because koin use a old version of stately-common
            implementation("io.realm.kotlin:library-base:2.0.0")
        }

        androidMain.dependencies {
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.sqldelight.android)
        }

        androidUnitTest.dependencies {
            implementation("app.cash.sqldelight:sqlite-driver:2.0.0")
        }

        jvmMain.dependencies {
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.ktor.client.okhttp)
            implementation("app.cash.sqldelight:sqlite-driver:2.0.0")
        }

        /*jsMain.dependencies {
            implementation(libs.ktor.client.js)
            implementation(libs.sqldelight.js)
            implementation(npm("sql.js", "1.6.2"))
            implementation(devNpm("copy-webpack-plugin", "9.1.0"))
        }*/

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.sqldelight.native)
        }

        iosTest.dependencies {
            implementation(libs.sqldelight.native)
        }

        /*mingwMain.dependencies {
            implementation(libs.ktor.client.winhttp)
            implementation(libs.sqldelight.native)
        }

        mingwTest.dependencies {
            implementation(libs.sqldelight.native)
        }*/

    }

    //https://kotlinlang.org/docs/native-objc-interop.html#export-of-kdoc-comments-to-generated-objective-c-headers
    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        compilations["main"].compilerOptions.options.freeCompilerArgs.add("-Xexport-kdoc")
    }

}

android {
    namespace = "my.company.name"
    compileSdk = 34

    defaultConfig {
        minSdk = 21
    }
}
dependencies {
    implementation(libs.monitor)
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("my.company.name")
        }

    }
}

