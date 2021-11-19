package com.roche.sample.app.test

import androidx.test.espresso.Espresso
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.applitools.eyes.android.espresso.Eyes;
import com.roche.ssg.sample.MainActivity
import org.junit.Rule
import org.junit.Test
import com.applitools.eyes.android.components.androidx.AndroidXComponentsProvider
import com.roche.sample.app.pages.BiometricScreenPage
import com.roche.sample.app.utilites.AppUtils
import com.roche.sample.app.utilites.BaseTest
import com.roche.ssg.sample.R


class TestApplitools : BaseTest(){

    @get:Rule

    val activityTestRuleMain = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun simpleTest() {

        // Initialize the eyes SDK and set your private API key.
        val eyes = Eyes()
        eyes.apiKey = "ITEkr8sSbDFdVE2Pe99BZKgEH0X1Bzqzog9K7UntvYRw110"

        //Configuring Eyes
        eyes.setComponentsProvider(AndroidXComponentsProvider())

       try {

           //Open the connection with applitools server
           eyes.open("Sample App!", "verify Sample App Home page")

           eyes.checkWindow("Sample App Home Page")


           // Open Drawer to click on navigation.
           Espresso.onView(ViewMatchers.withId(R.id.main_drawer_layout)).perform(DrawerActions.open());

           eyes.checkWindow("Open Menu bar")

           // End the test.
           eyes.close();

       }finally {
           // If the test was aborted before eyes.close was called, ends the test as aborted.
           eyes.abortIfNotClosed();
       }
    }


}