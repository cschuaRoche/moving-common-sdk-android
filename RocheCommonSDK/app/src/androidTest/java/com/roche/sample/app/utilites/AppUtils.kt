package com.roche.sample.app.utilites

import androidx.annotation.IdRes
import androidx.test.platform.app.InstrumentationRegistry

object AppUtils {

    /**
     * Returns string value of given string resource id
     */
    fun string(@IdRes res: Int): String = InstrumentationRegistry.getInstrumentation().targetContext.getString(res)

    /**
     * Returns string value of given string resource id
     */
    fun string(@IdRes res: Int, vararg formatArgs: Any): String =
        InstrumentationRegistry.getInstrumentation().targetContext.getString(res, *formatArgs)
}