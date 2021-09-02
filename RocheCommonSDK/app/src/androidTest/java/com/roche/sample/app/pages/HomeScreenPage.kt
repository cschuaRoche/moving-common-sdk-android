package com.roche.sample.app.pages

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.roche.roche.dis.R
import com.roche.sample.app.utilites.AppUtils
import com.roche.sample.app.utilites.BaseTest
import com.roche.sample.app.utilites.performClick
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.anyOf


object HomeScreenPage : BaseTest(){

   fun  verifyHomeScreenTitle() {
       verifyIsDisplayed(withId(R.id.toolbar_title), isSleepRequired = true)
   }

   fun verifyHomeScreenTitleText() {
       verifyText(withId(R.id.toolbar_title),AppUtils.string(R.string.text_app_name),isSleepRequired = true)
   }

   fun verifyRocheIconIsDisplayed() {
       verifyIsDisplayed(withId(R.id.toolbar_right_button),isSleepRequired = true)
   }

    fun verifySampleAppMainText() {
        verifyText(withText(R.string.text_main_description),"This Sample App will demo the features and functionalities of the SSG Android common libraries.",isSleepRequired = true)
    }

    fun VerifyAndClickBiometricMenu() {
       onView(allOf(withId(R.id.biometrics_nav_f), withChild(withText(R.string.text_biometric_menu)))).performClick()
    }

    fun verifyAndClickRecallMenu() {
        onView(allOf(withId(R.id.recallFragment), withChild(withText(R.string.text_recall)))).performClick()
    }

    fun verifyAndClickUtilitiesMenu() {
        onView(allOf(withId(R.id.utilsFragment), withChild(withText(R.string.text_utilities)))).performClick()
    }

}

