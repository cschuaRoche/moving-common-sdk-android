package com.roche.sample.app.utilites

import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher

fun ViewInteraction.performClick(): ViewInteraction = perform(ViewActions.click())

fun Matcher<View>.matchView(): ViewInteraction = Espresso.onView(this)

fun Matcher<View>.performClick(): ViewInteraction = matchView().performClick()

fun Matcher<View>.isDisplayed(): ViewInteraction = matchView().check(
    ViewAssertions.matches(
        ViewMatchers.isDisplayed()
    )
)

fun Matcher<View>.performTextType(input: String): ViewInteraction = matchView().perform(
    ViewActions.typeText(input),
    ViewActions.closeSoftKeyboard()
)

fun Matcher<View>.hasText(expectedText: String): ViewInteraction = matchView().check(
    ViewAssertions.matches(
        ViewMatchers.withText(
            expectedText
        )
    )
)

fun Matcher<View>.isEnabled(): ViewInteraction = matchView().check(
    ViewAssertions.matches(
        ViewMatchers.isEnabled()
    )
)

fun Matcher<View>.substringText(expectedText: String): ViewInteraction = matchView().check(
    ViewAssertions.matches(
        ViewMatchers.withSubstring(
            expectedText
        )
    )
)

fun Matcher<View>.isScrollTo(): ViewInteraction = matchView().perform(ViewActions.scrollTo())

fun Matcher<View>.performClearText(): ViewInteraction = matchView().perform(
    ViewActions.clearText(),
    ViewActions.closeSoftKeyboard()
)