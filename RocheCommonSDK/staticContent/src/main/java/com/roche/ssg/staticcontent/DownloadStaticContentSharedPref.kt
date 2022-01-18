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
    internal const val PREF_KEY_VERSION_PREFIX = "key_version"

    @VisibleForTesting
    internal const val PREF_KEY_ETAG_PREFIX = "key_etag"

    @VisibleForTesting
    internal const val PREF_KEY_FILE_PATH_PREFIX = "key_file_path"

    @VisibleForTesting
    internal const val PREF_KEY_CANCEL_DOWNLOAD = "key_cancel_download"

    fun getVersion(context: Context, targetSubDir: String): String {
        val key = "${PREF_KEY_VERSION_PREFIX}_$targetSubDir"
        val pref = PreferenceUtil.createOrGetPreference(context, USER_MANUALS_PREFS)
        return pref.get(key, "") ?: ""
    }

    fun setVersion(context: Context, targetSubDir: String, appVersion: String) {
        val key = "${PREF_KEY_VERSION_PREFIX}_$targetSubDir"
        val pref = PreferenceUtil.createOrGetPreference(context, USER_MANUALS_PREFS)
        pref.set(key, appVersion)
    }

    fun getETag(
        context: Context,
        targetSubDir: String,
        appVersion: String,
        locale: String,
        fileKey: String
    ): String {
        val key = generateKey(PREF_KEY_ETAG_PREFIX, targetSubDir, appVersion, locale, fileKey)
        val pref = PreferenceUtil.createOrGetPreference(context, USER_MANUALS_PREFS)
        return pref.get(key, "") ?: ""
    }

    fun setETag(
        context: Context,
        targetSubDir: String,
        appVersion: String,
        locale: String,
        fileKey: String,
        eTag: String
    ) {
        val key = generateKey(PREF_KEY_ETAG_PREFIX, targetSubDir, appVersion, locale, fileKey)
        val pref = PreferenceUtil.createOrGetPreference(context, USER_MANUALS_PREFS)
        pref.set(key, eTag)
    }

    fun getFilePath(
        context: Context,
        targetSubDir: String,
        appVersion: String,
        locale: String,
        fileKey: String
    ): String {
        val key = generateKey(PREF_KEY_FILE_PATH_PREFIX, targetSubDir, appVersion, locale, fileKey)
        val pref = PreferenceUtil.createOrGetPreference(context, USER_MANUALS_PREFS)
        return pref.get(key, "") ?: ""
    }

    fun setFilePath(
        context: Context,
        targetSubDir: String,
        appVersion: String,
        locale: String,
        fileKey: String,
        filePath: String
    ) {
        val key = generateKey(PREF_KEY_FILE_PATH_PREFIX, targetSubDir, appVersion, locale, fileKey)
        val pref = PreferenceUtil.createOrGetPreference(context, USER_MANUALS_PREFS)
        pref.set(key, filePath)
    }

    fun setCancelDownload(
        context: Context,
        targetSubDir: String,
        appVersion: String,
        locale: String,
        fileKey: String,
        shouldCancel: Boolean
    ) {
        val key = generateKey(PREF_KEY_CANCEL_DOWNLOAD, targetSubDir, appVersion, locale, fileKey)
        val pref = PreferenceUtil.createOrGetPreference(context, USER_MANUALS_PREFS)
        pref.set(key, shouldCancel)
    }

    fun isDownloadCancelled(
        context: Context,
        targetSubDir: String,
        appVersion: String,
        locale: String,
        fileKey: String
    ): Boolean {
        val key = generateKey(PREF_KEY_CANCEL_DOWNLOAD, targetSubDir, appVersion, locale, fileKey)
        val pref = PreferenceUtil.createOrGetPreference(context, USER_MANUALS_PREFS)
        return pref.get(key, false) ?: false
    }

    fun removeAllKeysOfAppVersion(context: Context, targetSubDir: String, appVersion: String) {
        val pref = PreferenceUtil.createOrGetPreference(context, USER_MANUALS_PREFS)
        for (key in pref.all.keys) {
            if (key.startsWith("${PREF_KEY_ETAG_PREFIX}_${targetSubDir}_${getAppVersionKey(appVersion)}_") ||
                key.startsWith("${PREF_KEY_FILE_PATH_PREFIX}_${targetSubDir}_${getAppVersionKey(appVersion)}_")||
                key.startsWith("${PREF_KEY_CANCEL_DOWNLOAD}_${targetSubDir}_${getAppVersionKey(appVersion)}_")
            ) {
                pref.remove(key)
            }
        }
    }

    private fun generateKey(
        prefix: String,
        targetSubDir: String,
        appVersion: String,
        locale: String,
        fileKey: String
    ) = "${prefix}_${targetSubDir}_${getAppVersionKey(appVersion)}_${locale}_${fileKey}"

    private fun getAppVersionKey(appVersion: String) = appVersion.replace(".", "_")
}