package com.roche.sample.app.utilites

import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.*
import com.roche.roche.dis.R
import junit.framework.AssertionFailedError
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.rules.TestName


open class BaseTest : UtiliteTest() {

    @get:Rule
    val testName = TestName()


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

    fun verifyText(matcher: Matcher<View>, expectedText: String, isSleepRequired: Boolean = false){
        if (isSleepRequired) {
            waitForText(matcher, expectedText)
        }
        matcher.hasText(expectedText)
    }

    fun clickonMainMenu() {
        // Open Drawer to click on navigation.
        onView(withId(R.id.main_drawer_layout)).perform(DrawerActions.open());
    }


}
