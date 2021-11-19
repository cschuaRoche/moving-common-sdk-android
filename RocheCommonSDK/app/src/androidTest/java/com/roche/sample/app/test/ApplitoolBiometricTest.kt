package com.roche.sample.app.test

import android.util.Log
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.roche.sample.app.pages.BiometricScreenPage
import com.roche.sample.app.pages.HomeScreenPage
import com.roche.sample.app.utilites.AppUtils
import com.roche.sample.app.utilites.BaseTest
import com.roche.ssg.sample.MainActivity
import com.roche.ssg.sample.R
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ApplitoolBiometricTest : BaseTest() {

    @get:Rule

    val activityTestRuleMain = ActivityScenarioRule(MainActivity::class.java)

    @Test
   fun testBiometricPageUI() {
        launchApp()
        homeScreenUI()
        HomeScreenPage.verifySampleAppMainText()
        clickonMainMenu()
        openMenubarUI()
        HomeScreenPage.VerifyAndClickBiometricMenu()
        BiometricScreenPage.verifyBiometricsPage()
        BiometricScreenPage.clickOnFingerPrintSupported()
        BiometricScreenPage.clickOnFaceSupported()
        BiometricScreenPage.clickOnIrisSupported()
        BiometricScreenPage.clickOnIsBiomtericEnrolled()
        BiometricsScreenUIAfterStatusDisplay()
        BiometricScreenPage.verifyFingerPrintStatus(AppUtils.string(R.string.text_status_true))
        BiometricScreenPage.verifyFaceSupportedStatus(AppUtils.string(R.string.text_status_false))
        BiometricScreenPage.verifyIrisSupportedStatus(AppUtils.string(R.string.text_status_false))
        BiometricScreenPage.verifyIsBiometricEnrolledStatus(AppUtils.string(R.string.text_status_false))
        termiateApp()
    }

    @Test

    fun testAuthenticationBiometric(){
        launchApp()
        BiometricOpenApplitool()
        HomeScreenPage.verifySampleAppMainText()
        clickonMainMenu()
        openMenubarUI()
        HomeScreenPage.VerifyAndClickBiometricMenu()
        BiometricsScreenUIStatusDisplay()
        BiometricScreenPage.clickOnEnrollBiometric()
        AppUtils.redirectToFingerEnrollmentScreen()
        BiometricScreenPage.verifyEnrollBiometricButtonText()
        BiometricScreenPage.clickOnAuthenticate()
        AppUtils.authenticateWithFingerprint()
        authenticateStatusUI()
        authenticateStatusElementUI()
        BiometricScreenPage.verifyAuthenticateStatus(AppUtils.string(R.string.text_successful_status_number))
        Log.i(AppUtils.TAG, "Stop Fingerprint")
        AppUtils.setSecurityLockToNone()
        termiateApp()
    }
}