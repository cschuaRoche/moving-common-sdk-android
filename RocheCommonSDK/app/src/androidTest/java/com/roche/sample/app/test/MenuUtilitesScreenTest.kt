package com.roche.sample.app.test

import android.view.Menu
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.roche.roche.dis.MainActivity
import com.roche.roche.dis.R
import com.roche.sample.app.pages.HomeScreenPage
import com.roche.sample.app.pages.MenuUtilitesScreenPage
import com.roche.sample.app.utilites.AppUtils
import com.roche.sample.app.utilites.BaseTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MenuUtilitesScreenTest : BaseTest() {

    @get:Rule
    val activityTestRuleMain = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun TestUtilitesScreenFunctionality() {
        HomeScreenPage.verifyHomeScreenTitle()
        HomeScreenPage.verifySampleAppMainText()
        clickonMainMenu()
        HomeScreenPage.verifyAndClickUtilitiesMenu()
        MenuUtilitesScreenPage.verifyUtitliesScreenPage()
        MenuUtilitesScreenPage.clickOnCheckDeviceRootedButton()
        MenuUtilitesScreenPage.clickOnGetUserManualButton()
        MenuUtilitesScreenPage.clickOnUnZIPButton()
        MenuUtilitesScreenPage.verifyDeviceRootedStatus(AppUtils.string(R.string.text_device_rooted))
        MenuUtilitesScreenPage.verifyUnZipStatus(AppUtils.string(R.string.text_unzip_status_path))
        MenuUtilitesScreenPage.verifyGetUserManualStatus(AppUtils.string(R.string.text_user_manual_status))
    }

}