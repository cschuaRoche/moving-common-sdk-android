package com.roche.sample.app.utilites

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.applitools.eyes.android.components.androidx.AndroidXComponentsProvider
import com.applitools.eyes.android.espresso.Eyes
import com.roche.ssg.sample.R
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.rules.TestName


open class BaseTest : UtiliteTest() {

    @get:Rule
    val testName = TestName()
    // Initialize the eyes SDK and set your private API key.
    val eyes = Eyes()

    fun clickOnButton(matcher: Matcher<View>, isSleepRequired: Boolean = false) {
        if (isSleepRequired) {
            waitForID(matcher)
        }
        matcher.performClick()
    }

    fun verifyIsDisplayed(matcher: Matcher<View>, isSleepRequired: Boolean = false) {
        if (isSleepRequired) {
            waitForID(matcher)
        }
        matcher.isDisplayed()
    }

    fun verifyText(matcher: Matcher<View>, expectedText: String, isSleepRequired: Boolean = false) {
        if (isSleepRequired) {
            waitForText(matcher, expectedText)
        }
        matcher.hasText(expectedText)
    }

    fun clickonMainMenu() {
        // Open Drawer to click on navigation.
        onView(withId(R.id.main_drawer_layout)).perform(DrawerActions.open());
    }

    fun launchApp() {
        eyes.apiKey = "ITEkr8sSbDFdVE2Pe99BZKgEH0X1Bzqzog9K7UntvYRw110"
        //Configuring Eyes
        eyes.setComponentsProvider(AndroidXComponentsProvider())
    }

    fun termiateApp() {
        try{
           eyes.close()
        }catch(e: Exception){
            eyes.abortIfNotClosed()
        }
    }

    fun homeScreenUI() {
        eyes.open("Sample App!", "Biometrics Page Check")
        eyes.checkWindow("Sample App!")
    }

    fun openMenubarUI() {
        eyes.checkWindow("Open Slide Menu bar")
    }

    fun BiometricsScreenUIStatusDisplay(){
        eyes.checkWindow("Biometrics Default Screen!")
    }

    fun BiometricsScreenUIAfterStatusDisplay(){
        eyes.checkWindow("After Biometrics Screen Status Check!")
    }

    fun clickOnAfterEnrollBiometricUI(){
        eyes.checkWindow("Click on Enroll Biometric button and redirect to setting page")
    }

    fun clickOnAfterAuthenticateUI() {
        eyes.checkWindow("Finger print Screen Pop up")
    }

    fun authenticateStatusElementUI() {
        eyes.checkElement(R.id.btn_authenticate,"Authenticate Button Text")
        eyes.checkElement(R.id.txt_status_authenticate,"Authenticate Status")
    }

    fun authenticateStatusUI() {
        eyes.checkWindow("Authenticate Status Successfully")
    }

    fun BiometricOpenApplitool() {
        eyes.open("Sample App!", "Enroll Authenticate Biometric Check")
        eyes.checkWindow("Sample App Home Page")
    }


}
