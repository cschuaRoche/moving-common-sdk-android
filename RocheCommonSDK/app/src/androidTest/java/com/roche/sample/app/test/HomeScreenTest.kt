package com.roche.sample.app.test

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.roche.roche.dis.MainActivity
import com.roche.roche.dis.R
import com.roche.sample.app.pages.HomeScreenPage
import com.roche.sample.app.utilites.AppUtils
import com.roche.sample.app.utilites.BaseTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest : BaseTest(){

    @get:Rule

    val activityTestRuleMain = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun testSampleAppHomeScreen() {
       HomeScreenPage.verifyHomeScreenTitle()
       HomeScreenPage.verifyHomeScreenTitleText()
       HomeScreenPage.verifyRocheIconIsDisplayed()
       HomeScreenPage.verifyBiometricToogleBtn()
       clickonMainMenu()
       HomeScreenPage.VerifyAndClickBiometricMenu()
       clickonMainMenu()
       HomeScreenPage.VerifyAndClickUnZipMenu()
       clickonMainMenu()
       HomeScreenPage.VerifyandClickMenuUserManual()
    }

    @Test
    fun testClickOnToogleBiometric() {
        HomeScreenPage.verifyHomeScreenTitle()
        HomeScreenPage.verifyBiometricToogleBtn()
        HomeScreenPage.clickOnBiometricToogleBtn()
        HomeScreenPage.verifyPopHeaderText()
    }


}