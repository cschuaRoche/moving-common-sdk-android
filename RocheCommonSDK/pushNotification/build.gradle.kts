import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

val versions = rootProject.ext["versions"] as HashMap<String, Any>

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    kotlin("plugin.serialization") version "1.4.10"
}

version = "1.0"

kotlin {
    android()

    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget = when {
        System.getenv("SDK_NAME")?.startsWith("iphoneos") == true -> ::iosArm64
        System.getenv("NATIVE_ARCH")?.startsWith("arm") == true -> ::iosSimulatorArm64
        else -> ::iosX64
    }

    iosTarget("ios") {}

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        frameworkName = "pushNotification"
        // set path to your ios project podfile, e.g. podfile = project.file("../iosApp/Podfile")
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                // ktor
                implementation("io.ktor:ktor-client-core:${versions["ktor_version"]}")
                implementation("io.ktor:ktor-client-logging:${versions["ktor_version"]}")
                implementation("io.ktor:ktor-client-serialization:${versions["ktor_version"]}")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation( "io.ktor:ktor-client-mock:${versions["ktor_version"]}")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-android:${versions["ktor_version"]}")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }
        val iosMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:${versions["ktor_version"]}")
            }
        }
        val iosTest by getting
    }
}

android {
    compileSdkVersion(versions["compile_sdk_version"].toString().toInt())
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(versions["min_sdk_version"].toString().toInt())
        targetSdkVersion(versions["target_sdk_version"].toString().toInt())
    }
}