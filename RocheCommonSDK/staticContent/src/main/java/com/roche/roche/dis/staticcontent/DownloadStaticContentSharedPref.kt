package com.roche.roche.dis.staticcontent

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.roche.roche.dis.utils.PreferenceUtil
import com.roche.roche.dis.utils.get
import com.roche.roche.dis.utils.set

object DownloadStaticContentSharedPref {
    private const val UNDERSCORE = "_"

    @VisibleForTesting
    internal const val USER_MANUALS_PREFS = "USER_MANUALS_PREFS"
    @VisibleForTesting
    internal const val PREF_KEY_ETAG_PREFIX = "key_etag"
    @VisibleForTesting
    internal const val PREF_KEY_FILE_PATH_PREFIX = "key_file_path"

    fun getETag(context: Context, appVersion: String, @DownloadStaticContent.LocaleType locale: String): String {
        val key = generateKey(PREF_KEY_ETAG_PREFIX, appVersion, locale)
        val pref = PreferenceUtil.createOrGetPreference(context, USER_MANUALS_PREFS)
        return pref.get(key, "") ?: ""
    }

    fun saveETag(context: Context, appVersion: String, @DownloadStaticContent.LocaleType locale: String, eTag: String) {
        val key = generateKey(PREF_KEY_ETAG_PREFIX, appVersion, locale)
        val pref = PreferenceUtil.createOrGetPreference(context, USER_MANUALS_PREFS)
        pref.set(key, eTag)
    }

    fun getDownloadedFilePath(
        context: Context,
        appVersion: String,
        @DownloadStaticContent.LocaleType locale: String
    ): String {
        val key = generateKey(PREF_KEY_FILE_PATH_PREFIX, appVersion, locale)
        val pref = PreferenceUtil.createOrGetPreference(context, USER_MANUALS_PREFS)
        return pref.get(key, "") ?: ""
    }

    fun saveDownloadedFilePath(
        context: Context,
        appVersion: String,
        @DownloadStaticContent.LocaleType locale: String,
        filePath: String
    ) {
        val key = generateKey(PREF_KEY_FILE_PATH_PREFIX, appVersion, locale)
        val pref = PreferenceUtil.createOrGetPreference(context, USER_MANUALS_PREFS)
        pref.set(key, filePath)
    }

    private fun generateKey(
        prefix: String,
        appVersion: String,
        @DownloadStaticContent.LocaleType locale: String
    ): String {
        return prefix + UNDERSCORE + getAppVersionKey(appVersion) + UNDERSCORE + locale
    }

    private fun getAppVersionKey(appVersion: String) = appVersion.replace(".", "_")
}