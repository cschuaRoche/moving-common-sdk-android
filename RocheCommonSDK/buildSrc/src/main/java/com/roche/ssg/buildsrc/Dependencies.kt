package com.roche.ssg.buildsrc

/**
 * To define plugins
 */
object BuildPlugins {
    const val android = "com.android.tools.build:gradle:${Versions.gradlePlugin}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val buildInfoExtractorGradle =
        "org.jfrog.buildinfo:build-info-extractor-gradle:${Versions.buildInfoExtractorGradle}"
    const val dokkaGradlePlugin =
        "org.jetbrains.dokka:dokka-gradle-plugin:${Versions.dokkaGradlePlugin}"
    const val navigationSafeArgsGradlePlugin =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
    const val firebaseAppDistributionGradle =
        "com.google.firebase:firebase-appdistribution-gradle:${Versions.firebaseAppDistributionGradle}"
    const val googleServices = "com.google.gms:google-services:${Versions.gms}"
    const val orgJacocoCore = "org.jacoco:org.jacoco.core:${Versions.jacoco}"
    const val hiltAndroidGradlePlugin =
        "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
}

/**
 * To define dependencies
 */
object Deps {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val materialDesign = "com.google.android.material:material:${Versions.material}"
    const val junit = "junit:junit:${Versions.junit}"
    const val testRules = "com.android.support.test:rules:${Versions.testRules}"
    const val espressoContrib =
        "com.android.support.test.espresso:espresso-contrib:${Versions.espressoContrib}"
    const val gson = "com.google.code.gson:gson:${Versions.gsonVersion}"
    const val splitAndroidClient = "io.split.client:android-client:${Versions.splitVersion}"
    const val awsAuthCognito = "com.amplifyframework:aws-auth-cognito:${Versions.amplify}"
    const val javaxInject = "javax.inject:javax.inject:${Versions.javaxInject}"
    const val kotlinxCoroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinxCoroutinesCore}"
    const val kotlinxCoroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinxCoroutinesCore}"
    const val json = "org.json:json:${Versions.json}"


    object AndroidX {

        const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
        const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
        const val legacySupportV4 = "androidx.legacy:legacy-support-v4:${Versions.legacySupportV4}"
        const val activityKtx = "androidx.activity:activity-ktx:${Versions.activityKtx}"
        const val localBroadcastManager =
            "androidx.localbroadcastmanager:localbroadcastmanager:${Versions.localBroadcastManager}"

        object Navigation {
            const val navigationFragmentKtx =
                "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
            const val navigationUiKtx =
                "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
        }

        object Biometric {
            const val biometric = "androidx.biometric:biometric:${Versions.biometric}"
        }

        object Security {
            const val securityCrypto =
                "androidx.security:security-crypto:${Versions.securityCrypto}"
        }

        object Test {
            const val uiautomator = "androidx.test.uiautomator:uiautomator:${Versions.uiAutomator}"
            const val testExt = "androidx.test.ext:junit:${Versions.junit}"
            const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
            const val core = "androidx.test:core:${Versions.testCore}"
            const val coreTesting = "androidx.arch.core:core-testing:${Versions.coreTesting}"

        }
    }

    object Firebase {
        const val firebaseBom = "com.google.firebase:firebase-bom:${Versions.firebase}"
        const val firebaseAnalyticsKtx = "com.google.firebase:firebase-analytics-ktx"
        const val firebaseMessaging = "com.google.firebase:firebase-messaging"
    }

    object Salesforce {
        const val chatCore = "com.salesforce.service:chat-core:4.2.0"
        const val chatUi = "com.salesforce.service:chat-ui:4.2.0"
    }

    object RocheCommonComponent {
        const val utils = "RocheCommonComponent:utils:${Versions.RocheCommonComponent.utils}"
    }

    object Hilt {
        const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
    }

    object Amplitude {
        const val androidSdk = "com.amplitude:android-sdk:${Versions.amplitudeAndroidSdk}"
        const val experimentAndroidClient =
            "com.amplitude:experiment-android-client:${Versions.amplitudeExperimentAndroidClient}"
    }

    object Mockito {
        const val mockitoCore = "org.mockito:mockito-core:${Versions.mockitoCore}"
        const val mockk = "io.mockk:mockk:${Versions.mockk}"
    }

    object SquareUp {
        const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
        const val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    }

    object Ktor {
        const val clientCore = "io.ktor:ktor-client-core:${Versions.ktorVersion}"
        const val clientLogging = "io.ktor:ktor-client-logging:${Versions.ktorVersion}"
        const val clientSerialization = "io.ktor:ktor-client-serialization:${Versions.ktorVersion}"
        const val clientMock = "io.ktor:ktor-client-mock:${Versions.ktorVersion}"
        const val clientAndroid = "io.ktor:ktor-client-android:${Versions.ktorVersion}"
        const val clientIos = "io.ktor:ktor-client-ios:${Versions.ktorVersion}"
    }

}
