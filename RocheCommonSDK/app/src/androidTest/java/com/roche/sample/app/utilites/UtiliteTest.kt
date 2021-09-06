package com.roche.sample.app.utilites

import android.util.Log
import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.uiautomator.*
import junit.framework.AssertionFailedError
import org.hamcrest.Matcher

open class UtiliteTest {


    fun ViewInteraction.isDisplayed(): Boolean {
        return try {
            check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            true
        } catch (e: NoMatchingViewException) {
            false
        }
    }

    fun waitForID(matcher: Matcher<View>) {
        for (i in 1..20) {
            Log.e("Times: $i", "wait for id is started $matcher")
            Thread.sleep(500)
            if (Espresso.onView(matcher).isDisplayed()) {
                Log.e("Times: $i", "try to get  this element ,In This time : $matcher")
                Espresso.onView(matcher).isDisplayed()
                break
            }
        }
    }

    fun waitForID(matcher: Matcher<View>, timer: Int) {
        for (i in 1..timer) {
            Log.e("Times: $i", "wait for id is started $matcher")
            Thread.sleep(500)
            if (Espresso.onView(matcher).isDisplayed()) {
                Log.e("Times: $i", "try to get  this element ,In This time : $matcher")
                Espresso.onView(matcher).isDisplayed()
                break
            }
        }
    }
    fun waitForText(matcher: Matcher<View>, expectedText: String) {
        for (i in 1..50) {
            Log.e("Times: $i", "wait for text is started $expectedText")
            Thread.sleep(500)
            if (verifyTextIsDisplayed(matcher, expectedText)) {
                Log.e("Times: $i", "try to get  this element ,In This time :$expectedText")
                Espresso.onView(matcher)
                    .check(ViewAssertions.matches(ViewMatchers.withText(expectedText)))
                break
            } else {
                Log.e("Timeth: $i", "Failed to wait For Text")
            }
        }
    }

    private fun verifyTextIsDisplayed(matcher: Matcher<View>, expectedText: String): Boolean {
        return try {
            Espresso.onView(matcher)
                .check(ViewAssertions.matches(ViewMatchers.withText(expectedText)))
            true
        } catch (e: AssertionFailedError) {
            Log.e("Timeth_catch", "Failed to wait For Text")
            false
        }
    }

}