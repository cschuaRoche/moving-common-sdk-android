package com.roche.sample.app.test

import android.util.Log
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.roche.roche.dis.MainActivity
import com.roche.roche.dis.R
import com.roche.sample.app.pages.BiometricScreenPage
import com.roche.sample.app.pages.HomeScreenPage
import com.roche.sample.app.utilites.AppUtils
import com.roche.sample.app.utilites.BaseTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BioMetricScreenTest : BaseTest() {

    @get:Rule

    val activityTestRuleMain = ActivityScenarioRule(MainActivity::class.java)


    @Before
    fun openBiometricPage() {
        HomeScreenPage.verifySampleAppMainText()
        clickonMainMenu()
        HomeScreenPage.VerifyAndClickBiometricMenu()
    }


    @After
    fun stopScript() {
        Log.i(AppUtils.TAG, "Stop Fingerprint")
        if (testName.methodName == "testEnrollBiometricButtonAndAuthenticateButtonFunctionality" || testName.methodName == "testEnrollBiometricButtonAndWrongAuthenticateButtonFunctionality" || testName.methodName == "testEnrollBiometricAndVerifyAllStatus")
            AppUtils.setSecurityLockToNone()
    }

    @Test
    fun testBioMetricScreenAndStatusAll() {
        BiometricScreenPage.verifyBiometricsPage()
        BiometricScreenPage.clickOnFingerPrintSupported()
        BiometricScreenPage.clickOnFaceSupported()
        BiometricScreenPage.clickOnIrisSupported()
        BiometricScreenPage.clickOnIsBiomtericEnrolled()
        BiometricScreenPage.verifyFingerPrintStatus(AppUtils.string(R.string.text_status_true))
        BiometricScreenPage.verifyFaceSupportedStatus(AppUtils.string(R.string.text_status_false))
        BiometricScreenPage.verifyIrisSupportedStatus(AppUtils.string(R.string.text_status_false))
        BiometricScreenPage.verifyIsBiometricEnrolledStatus(AppUtils.string(R.string.text_status_false))

    }

    @Test
    fun testAuthenticateButtonPopUpFunctionality() {
        BiometricScreenPage.verifyBiometricsPage()
        BiometricScreenPage.clickOnAuthenticate()
        BiometricScreenPage.verifyPopUpUI()
        BiometricScreenPage.clickOnCancelButton()
        BiometricScreenPage.verifyAuthenticateButtonText()
    }

    @Test
    fun testEnrollBiometricButtonAndAuthenticateButtonFunctionality() {
        BiometricScreenPage.clickOnEnrollBiometric()
        AppUtils.redirectToFingerEnrollmentScreen()
        BiometricScreenPage.verifyEnrollBiometricButtonText()
        BiometricScreenPage.verifyAuthenticateButtonText()
        BiometricScreenPage.clickOnAuthenticate()
        AppUtils.authenticateWithFingerprint()
        BiometricScreenPage.verifyAuthenticateButtonText()
        BiometricScreenPage.verifyAuthenticateStatus(AppUtils.string(R.string.text_successful_status_number))
    }

    @Test
    fun testEnrollBiometricButtonAndWrongAuthenticateButtonFunctionality() {
        BiometricScreenPage.clickOnEnrollBiometric()
        AppUtils.redirectToFingerEnrollmentScreen()
        BiometricScreenPage.verifyEnrollBiometricButtonText()
        BiometricScreenPage.verifyAuthenticateButtonText()
        BiometricScreenPage.clickOnAuthenticate()
        AppUtils.authenticateWrongFingerprint()
    }

    @Test
    fun testEnrollBiometricAndVerifyAllStatus() {
        BiometricScreenPage.clickOnEnrollBiometric()
        AppUtils.redirectToFingerEnrollmentScreen()
        BiometricScreenPage.verifyEnrollBiometricButtonText()
        BiometricScreenPage.clickOnFingerPrintSupported()
        BiometricScreenPage.clickOnFaceSupported()
        BiometricScreenPage.clickOnIrisSupported()
        BiometricScreenPage.clickOnIsBiomtericEnrolled()
        BiometricScreenPage.verifyFingerPrintStatus(AppUtils.string(R.string.text_status_true))
        BiometricScreenPage.verifyFaceSupportedStatus(AppUtils.string(R.string.text_status_false))
        BiometricScreenPage.verifyIrisSupportedStatus(AppUtils.string(R.string.text_status_false))
        BiometricScreenPage.verifyIsBiometricEnrolledStatus(AppUtils.string(R.string.text_status_true))
    }

}