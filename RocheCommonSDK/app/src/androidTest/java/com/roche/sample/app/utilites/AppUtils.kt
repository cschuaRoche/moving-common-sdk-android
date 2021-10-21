package com.roche.sample.app.utilites

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
import androidx.annotation.IdRes
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.*
import com.roche.ssg.sample.R


object AppUtils {

    const val TAG = "FingerprintTestTag"

    var uiDevice: UiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    /**
     * Returns string value of given string resource id
     */
    fun string(@IdRes res: Int): String =
        InstrumentationRegistry.getInstrumentation().targetContext.getString(res)

    /**
     * Returns string value of given string resource id
     */
    fun string(@IdRes res: Int, vararg formatArgs: Any): String =
        InstrumentationRegistry.getInstrumentation().targetContext.getString(res, *formatArgs)


    fun chooseScreenLockAsFingerprintPlusPin() {
        // security & location and find the fingerprint option and click on it
        val settingsList = UiScrollable(UiSelector().resourceId("com.android.settings:id/list"))
        val optionScreenLock: UiObject = settingsList.getChildByText(
            UiSelector().className("android.widget.RelativeLayout"),
            "Fingerprint"
        )
        optionScreenLock.click()

        // unlock with fingerprint screen, click on next button
        val fingerprintNextButton =
            uiDevice.findObject(UiSelector().resourceId("com.android.settings:id/fingerprint_next_button"))
        fingerprintNextButton.click()

        // choose screen lock as Fingerprint +PIN
        val screenLockOptionsList =
            UiScrollable(UiSelector().resourceId("com.android.settings:id/list"))
        val optionFingerprintPlusPin: UiObject = screenLockOptionsList.getChildByText(
            UiSelector().className("android.widget.RelativeLayout"),
            "Fingerprint + PIN"
        )
        optionFingerprintPlusPin.click()

        // security start-up screen click on yes
        val requiredPasswordButton =
            uiDevice.findObject(UiSelector().resourceId("com.android.settings:id/encrypt_require_password"))
        requiredPasswordButton.click()
        // required pin pop click on okay
        val confirmOkButton =
            uiDevice.findObject(UiSelector().resourceId("android:id/button1"))
        confirmOkButton.click()
    }

    private fun enterPin() {
        val etPin =
            uiDevice.findObject(UiSelector().resourceId("com.android.settings:id/password_entry"))
        etPin.text = "1234"

        val btnNext = uiDevice.wait(Until.findObject(By.text("NEXT").enabled(true)), 2000)
        btnNext.click()

        val etReenterPin =
            uiDevice.findObject(UiSelector().resourceId("com.android.settings:id/password_entry"))
        etReenterPin.text = "1234"

        val btnConfirm =
            uiDevice.wait(Until.findObject(By.text("CONFIRM").enabled(true)), 2000)
        btnConfirm.click()
    }

    private fun chooseLockScreenDisplay() {
        val btnDone = uiDevice.findObject(UiSelector().text("DONE"))
        btnDone.click()
    }

    private fun waitUntilFingerprintIsEnrolled() {
        Log.i(TAG, "Enroll Fingerprint")
        val btnDone: UiObject2? =
            uiDevice.wait(Until.findObject(By.text("DONE").enabled(true)), 60000)
        btnDone?.click()
        Log.i(TAG, "Enroll Fingerprint is done")
    }

    fun authenticateWithFingerprint() {
        Thread.sleep(2000)
        Log.i(TAG, "Authenticate Fingerprint")
        Thread.sleep(3000)
        Log.i(TAG, "Authenticate Fingerprint done")
    }

    fun authenticateWrongFingerprint() {
        Thread.sleep(2000)
        Log.i(TAG, "Authenticate Wrong Fingerprint")
        Thread.sleep(4000)
        Log.i(TAG, "Authenticate Wrong Fingerprint done")
    }

    fun redirectToFingerEnrollmentScreen() {
        chooseScreenLockAsFingerprintPlusPin()
        enterPin()
        chooseLockScreenDisplay()
        waitUntilFingerprintIsEnrolled()
        pressDeviceBack(2000)
    }

    fun pressDeviceBack(sleepTime: Long = 0L) {
        if (sleepTime > 0L) {
            Thread.sleep(sleepTime)
        }
        uiDevice.pressBack()
    }

    fun pressDeviceHome() {
        uiDevice.pressHome()
    }

    fun openRecentSampleSDKApp() {
        uiDevice.pressRecentApps()
        val selector = UiSelector()
        AppUtils.uiDevice.findObject(selector.descriptionStartsWith(AppUtils.string(R.string.test_app_package_name)))
            .clickAndWaitForNewWindow()
    }

    fun setSecurityLockToNone() {
        openSettings(uiDevice, Settings.ACTION_SECURITY_SETTINGS)
        val settingsList = UiScrollable(UiSelector().resourceId("com.android.settings:id/list"))
        val optionScreenLock: UiObject = settingsList.getChildByText(
            UiSelector().className("android.widget.RelativeLayout"),
            "Screen lock"
        )

        val currentScreenLockSetting =
            optionScreenLock.getChild(UiSelector().resourceId("android:id/summary")).text
        if (currentScreenLockSetting != "None") {
            optionScreenLock.click()
            val etPin =
                AppUtils.uiDevice.findObject(UiSelector().resourceId("com.android.settings:id/password_entry"))
            etPin.text = "1234"
            AppUtils.uiDevice.pressEnter()

            val chooseScreenLockList =
                UiScrollable(UiSelector().resourceId("com.android.settings:id/list"))
            val optionNone: UiObject = chooseScreenLockList.getChildByText(
                UiSelector().className("android.widget.RelativeLayout"),
                "None"
            )
            optionNone.click()

            val confirmOkButton =
                AppUtils.uiDevice.findObject(UiSelector().resourceId("android:id/button1"))
            confirmOkButton.click()
            pressDeviceBack(2000)
        }
    }


    /**
     * Open the android Settings
     */
    open fun openSettings(device: UiDevice, action: String) {
        // Start from the home screen
        device.pressHome()

        // Wait for launcher
        val launcherPackage: String = device.launcherPackageName!!
        device.wait(
            Until.hasObject(By.pkg(launcherPackage).depth(0)),
            5000
        )

        // Get context (of Home app ?).
        val context: Context = ApplicationProvider.getApplicationContext()

        val intent = Intent(action)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

}