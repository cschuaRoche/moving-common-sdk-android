package com.roche.ssg.sample.biometrics

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.VisibleForTesting
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey


object PreferenceUtil {

    /**
     * @param context need to create the SharedPreference
     * @param filename filename of the shared preference
     * return shared preference object
     */
    fun createOrGetPreference(context: Context, filename: String): SharedPreferences {

        return EncryptedSharedPreferences.create(
            context,
            filename,
            getMasterKey(context),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)
    }

    @VisibleForTesting
    internal fun getMasterKey(context: Context): MasterKey {
        return MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }
}

    /**
     * finds value on given key.
     * [T] is the type of value
     * @param defaultValue optional default value - will take null for strings, false for bool and -1 for numeric values if [defaultValue] is not specified
     */
     inline fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T? = null): T? {
        return when (T::class) {
            String::class -> getString(key, defaultValue as? String) as T?
            Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
            Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
            Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
            Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }


    /**
     * puts a key value pair in shared prefs if doesn't exists, otherwise updates value on given [key]
     */
    fun SharedPreferences.set(key: String, value: Any?) {
        when (value) {
            is String? -> edit ( true, {putString(key,value) })
            is Int -> edit ( true, {putInt(key,value) })
            is Boolean -> edit ( true, {putBoolean(key,value) })
            is Float -> edit ( true, {putFloat(key,value) })
            is Long -> edit ( true, {putLong(key,value) })
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
    }

    /**
     * removes key from shared prefs
     */
    fun SharedPreferences.remove(key: String) {
        edit(true) { remove(key) }
    }