package com.roche.roche.dis.staticcontent

import android.content.Context
import com.roche.roche.dis.utils.PreferenceUtil
import com.roche.roche.dis.utils.get
import com.roche.roche.dis.utils.set

object DownloadStaticContentSharedPref {
    const val USER_MANUALS_PREFS = "USER_MANUALS_PREFS"
    const val PREF_KEY_ETAG_PREFIX = "key_etag_"

    fun getETag(context: Context, key: String): String {
        val pref = PreferenceUtil.createOrGetPreference(context, USER_MANUALS_PREFS)
        return pref.get(key, "") ?: ""
    }

    fun saveETag(context: Context, key: String, eTag: String) {
        val pref = PreferenceUtil.createOrGetPreference(context, USER_MANUALS_PREFS)
        pref.set(key, eTag)
    }
}