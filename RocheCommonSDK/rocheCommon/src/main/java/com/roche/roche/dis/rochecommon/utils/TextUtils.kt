package com.roche.roche.dis.rochecommon.utils

import android.text.Html

class TextUtils {

    companion object {
        val NUMBERS_ONLY_PATTERN = Regex("[^0-9]")
        internal val customCountries = arrayListOf("US", "CA")
    }
}

fun String.fromHtml(): CharSequence = Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT) ?: ""

/**
 * @return The numbers in the string
 */
fun String.findNumbers(): String {
    return TextUtils.NUMBERS_ONLY_PATTERN.replace(this, "")
}

fun String.isCustomCountry() = TextUtils.customCountries.contains(this)
