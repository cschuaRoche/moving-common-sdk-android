package com.roche.sample.app.utilites

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.roche.ssg.sample.R
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


}
