package com.roche.sample.app.pages

import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.roche.ssg.sample.R
import com.roche.sample.app.utilites.AppUtils
import com.roche.sample.app.utilites.BaseTest

object BiometricScreenPage : BaseTest() {


    fun verifyFingerPrintSupportedButtonText() {
        verifyText(
            ViewMatchers.withId(R.id.btn_fingerprint_supported),
            AppUtils.string(R.string.text_fingerprint_supported), isSleepRequired = true
        )
    }

    fun verifyFaceSupportedButtonText() {
        verifyText(
            ViewMatchers.withId(R.id.btn_face_supported),
            AppUtils.string(R.string.text_face_supported), isSleepRequired = true
        )
    }

    fun verifyIrisSupportedButtonText() {
        verifyText(
            ViewMatchers.withId(R.id.btn_iris_supported),
            AppUtils.string(R.string.text_iris_supported), isSleepRequired = true
        )
    }

    fun verifyIsBioMetricEnrolledButtonText() {
        verifyText(
            ViewMatchers.withId(R.id.btn_biometric_enrolled),
            AppUtils.string(R.string.text_is_biometric_enrolled), isSleepRequired = true
        )
    }

    fun verifyEnrollBiometricButtonText() {
        verifyText(
            ViewMatchers.withId(R.id.btn_enroll_biometric),
            AppUtils.string(R.string.text_enroll_biometric), isSleepRequired = true
        )
    }

    fun verifyAuthenticateButtonText() {
        verifyText(
            ViewMatchers.withId(R.id.btn_authenticate),
            AppUtils.string(R.string.text_authenticate), isSleepRequired = true
        )
    }

    fun verifyBiometricsPage() {
        verifyFingerPrintSupportedButtonText()
        verifyFaceSupportedButtonText()
        verifyIrisSupportedButtonText()
        verifyIsBioMetricEnrolledButtonText()
        verifyEnrollBiometricButtonText()
        verifyAuthenticateButtonText()
    }

    fun clickOnFingerPrintSupported() {
        clickOnButton(withId(R.id.btn_fingerprint_supported), isSleepRequired = true)
    }

    fun clickOnFaceSupported() {
        clickOnButton(withId(R.id.btn_face_supported), isSleepRequired = true)
    }

    fun clickOnIrisSupported() {
        clickOnButton(withId(R.id.btn_iris_supported), isSleepRequired = true)
    }

    fun clickOnIsBiomtericEnrolled() {
        clickOnButton(withId(R.id.btn_biometric_enrolled), isSleepRequired = true)
    }

    fun clickOnEnrollBiometric() {
        clickOnButton(withId(R.id.btn_enroll_biometric), isSleepRequired = true)
    }

    fun clickOnAuthenticate() {
        clickOnButton(withId(R.id.btn_authenticate), isSleepRequired = true)
    }

    fun verifyFingerPrintStatus(status: String) {
        verifyText(withId(R.id.txt_status_fingerprint_supported), status, isSleepRequired = true)
    }

    fun verifyFaceSupportedStatus(status: String) {
        verifyText(withId(R.id.txt_status_face_supported), status, isSleepRequired = true)
    }

    fun verifyIrisSupportedStatus(status: String) {
        verifyText(withId(R.id.txt_status_iris_supported), status, isSleepRequired = true)
    }

    fun verifyIsBiometricEnrolledStatus(status: String) {
        verifyText(withId(R.id.txt_status_biometric_enrolled), status, isSleepRequired = true)
    }

    fun verifyEnrollBiometicStatus(status: String) {
        verifyText(withId(R.id.txt_status_enroll), status, isSleepRequired = true)
    }

    fun verifyAuthenticateStatus(status: String) {
        verifyText(withId(R.id.txt_status_authenticate), status, isSleepRequired = true)
    }

    fun verifyPopTitleText() {
        verifyText(
            ViewMatchers.withId(R.id.dialog_title),
            AppUtils.string(R.string.text_biometric_confirm_title)
        )
    }

    fun verifyPopDescriptionText() {
        verifyText(
            withId(R.id.dialog_description),
            AppUtils.string(R.string.text_biometric_enable_desc)
        )
    }

    fun verifyPopCancelButton() {
        verifyText(withId(R.id.dialog_btn_left), AppUtils.string(R.string.text_cancel))
    }

    fun verifyPopSettingsButton() {
        verifyText(withId(R.id.dialog_btn_right), AppUtils.string(R.string.text_settings))
    }

    fun verifyPopUpUI() {
        verifyPopTitleText()
        verifyPopDescriptionText()
        verifyPopCancelButton()
        verifyPopSettingsButton()
    }

    fun clickOnCancelButton() {
        clickOnButton(withId(R.id.dialog_btn_left), isSleepRequired = true)
    }

    fun verifyBiometricSuccessMsg() {
        verifyText(withId(R.string.text_biometric_already_enroll), "Biometric is already enrolled")
    }

}