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
    internal const val PREF_KEY_VERSION = "key_version"

    @VisibleForTesting
    internal const val PREF_KEY_ETAG_PREFIX = "key_etag"

    @VisibleForTesting
    internal const val PREF_KEY_FILE_PATH_PREFIX = "key_file_path"

    fun getVersion(context: Context): String {
        val pref = PreferenceUtil.createOrGetPreference(context, USER_MANUALS_PREFS)
        return pref.get(PREF_KEY_VERSION, "") ?: ""
    }

    fun setVersion(context: Context, appVersion: String) {
        val pref = PreferenceUtil.createOrGetPreference(context, USER_MANUALS_PREFS)
        pref.set(PREF_KEY_VERSION, appVersion)
    }

    fun getETag(
        context: Context,
        appVersion: String,
        @DownloadStaticContent.LocaleType locale: String,
        fileKey: String
    ): String {
        val key = generateKey(PREF_KEY_ETAG_PREFIX, appVersion, locale, fileKey)
        val pref = PreferenceUtil.createOrGetPreference(context, USER_MANUALS_PREFS)
        return pref.get(key, "") ?: ""
    }

    fun setETag(
        context: Context,
        appVersion: String,
        @DownloadStaticContent.LocaleType locale: String,
        fileKey: String,
        eTag: String
    ) {
        val key = generateKey(PREF_KEY_ETAG_PREFIX, appVersion, locale, fileKey)
        val pref = PreferenceUtil.createOrGetPreference(context, USER_MANUALS_PREFS)
        pref.set(key, eTag)
    }

    fun getFilePath(
        context: Context,
        appVersion: String,
        @DownloadStaticContent.LocaleType locale: String,
        fileKey: String
    ): String {
        val key = generateKey(PREF_KEY_FILE_PATH_PREFIX, appVersion, locale, fileKey)
        val pref = PreferenceUtil.createOrGetPreference(context, USER_MANUALS_PREFS)
        return pref.get(key, "") ?: ""
    }

    fun setFilePath(
        context: Context,
        appVersion: String,
        @DownloadStaticContent.LocaleType locale: String,
        fileKey: String,
        filePath: String
    ) {
        val key = generateKey(PREF_KEY_FILE_PATH_PREFIX, appVersion, locale, fileKey)
        val pref = PreferenceUtil.createOrGetPreference(context, USER_MANUALS_PREFS)
        pref.set(key, filePath)
    }

    fun removeAllKeysOfAppVersion(context: Context, appVersion: String) {
        val pref = PreferenceUtil.createOrGetPreference(context, USER_MANUALS_PREFS)
        for (key in pref.all.keys) {
            if (key.startsWith("${PREF_KEY_ETAG_PREFIX}_${getAppVersionKey(appVersion)}_") ||
                key.startsWith("${PREF_KEY_FILE_PATH_PREFIX}_${getAppVersionKey(appVersion)}_")
            ) {
                pref.remove(key)
            }
        }
    }

    private fun generateKey(
        prefix: String,
        appVersion: String,
        @DownloadStaticContent.LocaleType locale: String,
        fileKey: String
    ) = "${prefix}_${getAppVersionKey(appVersion)}_${locale}_${fileKey}"

    private fun getAppVersionKey(appVersion: String) = appVersion.replace(".", "_")
}