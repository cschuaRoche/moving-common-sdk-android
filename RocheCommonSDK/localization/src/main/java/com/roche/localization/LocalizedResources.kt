package com.roche.localization

import android.content.Context
import android.content.ContextWrapper
import androidx.annotation.StringRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.lokalise.sdk.Lokalise
import com.lokalise.sdk.LokaliseCallback
import com.lokalise.sdk.LokaliseContextWrapper
import com.lokalise.sdk.LokaliseResources
import com.lokalise.sdk.LokaliseUpdateError
import java.lang.RuntimeException

open class LocalizedResources : LifecycleObserver {

    companion object {
        private var isInitialize: Boolean = false

        fun initialise(appContext: Context, sdkToken: String, projectId: String) {
            Lokalise.init(
                appContext,
                sdkToken,
                projectId
            )
            isInitialize = true
        }

        fun isPreRelease(falg: Boolean) {
            if (!isInitialize) {
                throw RuntimeException("Lokalise is not initialize Please call initialise first.")
            }
            Lokalise.isPreRelease = falg
        }

        fun setLokaliseContextWrapper(context: Context): Context {
            return LokaliseContextWrapper.wrap(context)
        }
    }

    private val localiseCallback = object : LokaliseCallback {
        override fun onUpdateFailed(error: LokaliseUpdateError) {
            onTranslationsUpdateEnd(Exception(error.name))
        }

        override fun onUpdateNotNeeded() {
            onTranslationsUpdateEnd()
        }

        override fun onUpdated(oldBundleId: Long, newBundleId: Long) {
            onTranslationsUpdateEnd()
        }
    }

    open fun onTranslationsUpdateEnd(error: Exception? = null) {}

    open fun onTranslationsUpdateStart() {}

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun updateTranslations() {
        Lokalise.addCallback(localiseCallback)
        Lokalise.updateTranslations()
        onTranslationsUpdateStart()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun removeCallbacks() {
        Lokalise.removeCallback(localiseCallback)
    }

    fun getBaseContext(newBase: Context): ContextWrapper {
        return LokaliseContextWrapper.wrap(newBase)
    }
}

fun Context.getLocalisedString(@StringRes id: Int) =
    LokaliseResources(this).getString(id)