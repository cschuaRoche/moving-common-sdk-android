package com.roche.ssg.staticcontent

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.roche.ssg.utils.PreferenceUtil
import com.roche.ssg.utils.get
import com.roche.ssg.utils.remove
import com.roche.ssg.utils.set

object DownloadStaticContentSharedPref {
    @VisibleForTesting
    internal const val USER_MANUALS_PREFS = "USER_MANUALS_PREFS"

    @VisibleForTesting
    internal const val PREF_KEY_VERSION_PREFIX = "key_version_"

    @VisibleForTesting
    internal const val PREF_KEY_ETAG_PREFIX = "key_etag_"

    @VisibleForTesting
    internal const val PREF_KEY_FILE_PATH_PREFIX = "key_filePath_"

    fun getVersion(context: Context, targetSubDir: String): String {
        val key = PREF_KEY_VERSION_PREFIX + targetSubDir
        val pref = PreferenceUtil.createOrGetPreference(context, USER_MANUALS_PREFS)
        return pref.get(key, "") ?: ""
    }

    fun setVersion(context: Context, targetSubDir: String, appVersion: String) {
        val key = PREF_KEY_VERSION_PREFIX + targetSubDir
        val pref = PreferenceUtil.createOrGetPreference(context, USER_MANUALS_PREFS)
        pref.set(key, appVersion)
    }

    fun getETag(
        context: Context,
        generatedKey: String
    ): String {
        val key = PREF_KEY_ETAG_PREFIX + generatedKey
        val pref = PreferenceUtil.createOrGetPreference(context, USER_MANUALS_PREFS)
        return pref.get(key, "") ?: ""
    }

    fun setETag(
        context: Context,
        generatedKey: String,
        eTag: String
    ) {
        val key = PREF_KEY_ETAG_PREFIX + generatedKey
        val pref = PreferenceUtil.createOrGetPreference(context, USER_MANUALS_PREFS)
        pref.set(key, eTag)
    }

    fun getFilePath(
        context: Context,
        generatedKey: String
    ): String {
        val key = PREF_KEY_FILE_PATH_PREFIX + generatedKey
        val pref = PreferenceUtil.createOrGetPreference(context, USER_MANUALS_PREFS)
        return pref.get(key, "") ?: ""
    }

    fun setFilePath(
        context: Context,
        generatedKey: String,
        filePath: String
    ) {
        val key = PREF_KEY_FILE_PATH_PREFIX + generatedKey
        val pref = PreferenceUtil.createOrGetPreference(context, USER_MANUALS_PREFS)
        pref.set(key, filePath)
    }

    fun removeAllKeys(context: Context, generatedKey: String) {
        val pref = PreferenceUtil.createOrGetPreference(context, USER_MANUALS_PREFS)
        for (key in pref.all.keys) {
            if (key.startsWith(PREF_KEY_ETAG_PREFIX + generatedKey) ||
                key.startsWith(PREF_KEY_FILE_PATH_PREFIX + generatedKey)
            ) {
                pref.remove(key)
            }
        }
    }
}