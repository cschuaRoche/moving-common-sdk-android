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
    val ktor_version = "1.6.3"
    val napier_version = "2.1.0"

    android()

    val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64

    iosTarget("ios") {}

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        frameworkName = "AppRecall"
        // set path to your ios project podfile, e.g. podfile = project.file("../iosApp/Podfile")
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                // logging
                implementation("io.github.aakira:napier:$napier_version")

                // ktor
                implementation("io.ktor:ktor-client-core:$ktor_version")
                implementation("io.ktor:ktor-client-logging:$ktor_version")
                implementation("io.ktor:ktor-client-serialization:$ktor_version")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation( "io.ktor:ktor-client-mock:$ktor_version")
                implementation("io.mockk:mockk-common:1.9.3.kotlin12")
                implementation("io.mockk:mockk:1.9.3.kotlin12")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-android:$ktor_version")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
                implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")
            }
        }
        val iosMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:$ktor_version")
                implementation( "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2-native-mt")
            }
        }
        val iosTest by getting
    }
}

android {
    compileSdkVersion(31)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(31)
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
