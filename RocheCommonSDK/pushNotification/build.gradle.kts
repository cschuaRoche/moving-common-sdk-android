import com.roche.ssg.buildsrc.ConfigData
import com.roche.ssg.buildsrc.Deps
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    kotlin("plugin.serialization") version "1.4.10"
    id("maven-publish")
    id("com.jfrog.artifactory")
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
        frameworkName = "pushNotificationSDK"
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

artifactory {
    setContextUrl("https://dhs.jfrog.io/dhs/")
    publish(delegateClosureOf<org.jfrog.gradle.plugin.artifactory.dsl.PublisherConfig> {
        repository(delegateClosureOf<org.jfrog.gradle.plugin.artifactory.dsl.DoubleDelegateWrapper> {
            setProperty("repoKey", project.properties["artifactory.repokey"])
            setProperty("username", project.properties["artifactory.user"])
            setProperty("password", project.properties["artifactory.password"])
            //setProperty("maven", true)
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