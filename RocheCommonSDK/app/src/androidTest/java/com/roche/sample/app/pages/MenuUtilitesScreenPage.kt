package com.roche.sample.app.pages

import androidx.test.espresso.matcher.ViewMatchers
import com.roche.ssg.sample.R
import com.roche.sample.app.utilites.AppUtils
import com.roche.sample.app.utilites.BaseTest

object MenuUtilitesScreenPage : BaseTest() {

    fun verifyCheckDeviceRootedButtonText() {
        BiometricScreenPage.verifyText(
            ViewMatchers.withId(R.id.btn_is_rooted),
            AppUtils.string(R.string.text_is_rooted), isSleepRequired = true
        )
    }

    fun verifyUnZIPButtonText() {
        BiometricScreenPage.verifyText(
            ViewMatchers.withId(R.id.btn_unzip),
            AppUtils.string(R.string.text_unzip), isSleepRequired = true
        )
    }

//    fun verifyGetUserManualButtonText() {
//        BiometricScreenPage.verifyText(
//            ViewMatchers.withId(R.id.btn_user_manual),
//            AppUtils.string(R.string.text_get_statical_user_manual), isSleepRequired = true
//        )
//    }

    fun verifyUtitliesScreenPage() {
        verifyCheckDeviceRootedButtonText()
        verifyUnZIPButtonText()
      //  verifyGetUserManualButtonText()
    }


    fun clickOnCheckDeviceRootedButton() {
        clickOnButton(ViewMatchers.withId(R.id.btn_is_rooted), isSleepRequired = true)
    }

    fun clickOnUnZIPButton() {
        clickOnButton(ViewMatchers.withId(R.id.btn_unzip), isSleepRequired = true)
    }

//    fun clickOnGetUserManualButton() {
//        clickOnButton(ViewMatchers.withId(R.id.btn_user_manual), isSleepRequired = true)
//    }

    fun verifyUnZipStatus(status: String) {
        verifyText(ViewMatchers.withId(R.id.txt_unzip_status), status, isSleepRequired = true)
    }

//    fun verifyGetUserManualStatus(status: String) {
//        verifyText(ViewMatchers.withId(R.id.txt_user_manual_status), status, isSleepRequired = true)
//    }

    fun verifyDeviceRootedStatus(status: String) {
        verifyText(ViewMatchers.withId(R.id.txt_rooted_status), status, isSleepRequired = true)
    }
}