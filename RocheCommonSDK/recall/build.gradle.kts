import com.roche.ssg.buildsrc.ConfigData
import com.roche.ssg.buildsrc.Deps
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.4.10"
    kotlin("native.cocoapods")
    id("com.android.library")

    jacoco
}

jacoco {
    toolVersion = "0.8.7"
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
        ios.deploymentTarget = "12.0"
        frameworkName = "AppRecallSDK"
        // set path to your ios project podfile, e.g. podfile = project.file("../iosApp/Podfile")
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                // ktor
                implementation(Deps.Ktor.clientCore)
                implementation(Deps.Ktor.clientLogging)
                implementation(Deps.Ktor.clientSerialization)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(Deps.Ktor.clientMock)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Deps.Ktor.clientAndroid)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation(Deps.junit)
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(Deps.Ktor.clientIos)
            }
        }
        val iosTest by getting
    }
}

android {
    compileSdkVersion(ConfigData.compileSdkVersion)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(ConfigData.minSdkVersion)
        targetSdkVersion(ConfigData.targetSdkVersion)
    }
}

val jacocoTestReport by tasks.creating(JacocoReport::class.java) {
    val excludes = ArrayList<String>()
    File("${project.rootDir}/scripts/file_exclusion.txt").forEachLine { line ->
        if (!line.contains("//")) {
            excludes.add(line)
        }
    }

    val classFiles = fileTree(baseDir = "${buildDir}/tmp/kotlin-classes/debug/")
    classFiles.setExcludes(excludes)
    classDirectories.setFrom(classFiles)

    val coverageSourceDirs = arrayOf(
        "src/commonMain",
        "src/jvmMain"
    )

    sourceDirectories.setFrom(files(coverageSourceDirs))
    executionData
        .setFrom(files("${buildDir}/jacoco/testDebugUnitTest.exec"))

    reports {
        xml.isEnabled = true
        html.isEnabled = true
    }
}
