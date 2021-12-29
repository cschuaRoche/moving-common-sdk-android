import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

val versions = rootProject.ext["versions"] as HashMap<String, Any>

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    kotlin("plugin.serialization") version "1.4.10"
    id("maven-publish")
    id("com.jfrog.artifactory")
    jacoco
}

jacoco {
    toolVersion = "0.8.7"
}

version = "1.0.0"

kotlin {
    android {
        group = "RocheCommonComponent"
        publishLibraryVariants("release")
        mavenPublication {
            artifactId = project.name
            artifact("$buildDir/outputs/aar/${project.name}-release.aar")
        }
    }

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
                implementation("junit:junit:${versions["junit"]}")
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

artifactory {
    setContextUrl("https://dhs.jfrog.io/dhs/")
    publish(delegateClosureOf<org.jfrog.gradle.plugin.artifactory.dsl.PublisherConfig> {
        repository(delegateClosureOf<org.jfrog.gradle.plugin.artifactory.dsl.DoubleDelegateWrapper> {
            setProperty("repoKey", project.properties["artifactory.repokey"])
            setProperty("username", project.properties["artifactory.user"])
            setProperty("password", project.properties["artifactory.password"])
            setProperty("maven", true)
        })
        defaults(delegateClosureOf<groovy.lang.GroovyObject> {
            setPublishPom(true)
            invokeMethod(
                "publications", arrayOf(
                    "androidRelease"
                )
            )
            setProperty("publishArtifacts", true)
        })
    })
}
