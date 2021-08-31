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

   fun verifyBiometricToogleBtn() {
       verifyIsDisplayed(withId(R.id.biometric_btn),isSleepRequired = true)
   }

   fun clickOnBiometricToogleBtn() {
       clickOnButton(withId(R.id.biometric_btn),isSleepRequired = true)
   }

    fun VerifyAndClickBiometricMenu() {
       onView(allOf(withId(R.id.menu_biometrics), withChild(withText(R.string.text_biometric_menu)))).performClick()
    }

    fun VerifyAndClickUnZipMenu() {
        onView(allOf(withId(R.id.menu_unzip), withChild(withText(R.string.text_unzip_menu)))).performClick()
    }

    fun VerifyandClickMenuUserManual() {
        onView(allOf(withId(R.id.menu_user_manual), withChild(withText(R.string.text_getstatical_usermanual_menu)))).performClick()
    }

    fun verifyPopHeaderText() {
        verifyText(withText(R.string.text_biometric_confirm_title), "Do you want to allow \"RocheCommonSampleApp\" to use biometrics?",isSleepRequired = true)
    }


}

