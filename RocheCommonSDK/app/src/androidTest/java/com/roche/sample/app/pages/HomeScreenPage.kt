package com.roche.sample.app.pages

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.matcher.ViewMatchers.*
import com.roche.ssg.sample.R
import com.roche.sample.app.utilites.AppUtils
import com.roche.sample.app.utilites.BaseTest
import com.roche.sample.app.utilites.performClick
import org.hamcrest.CoreMatchers.allOf


object HomeScreenPage : BaseTest() {

    fun verifyHomeScreenTitle() {
        verifyIsDisplayed(withId(R.id.toolbar_title), isSleepRequired = true)
    }

    fun verifyHomeScreenTitleText() {
        verifyText(
            withId(R.id.toolbar_title),
            AppUtils.string(R.string.text_app_name),
            isSleepRequired = true
        )
    }

    fun verifyRocheIconIsDisplayed() {
        verifyIsDisplayed(withId(R.id.toolbar_right_button), isSleepRequired = true)
    }

    fun verifySampleAppMainText() {
        verifyText(
            withText(R.string.text_main_description),
            "This Sample App will demo the features and functionalities of the SSG Android common libraries.",
            isSleepRequired = true
        )
    }

    fun VerifyAndClickBiometricMenu() {
        onView(
            allOf(
                withId(R.id.biometrics_nav_f),
                withChild(withText(R.string.text_biometric_menu))
            )
        ).performClick()
    }

    fun verifyAndClickRecallMenu() {
        onView(
            allOf(
                withId(R.id.recallFragment),
                withChild(withText(R.string.text_recall))
            )
        ).performClick()
    }

    fun verifyAndClickUtilitiesMenu() {
        onView(
            allOf(
                withId(R.id.utilsFragment),
                withChild(withText(R.string.text_utilities))
            )
        ).performClick()
    }

}

