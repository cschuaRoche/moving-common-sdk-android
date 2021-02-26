package com.roche.roche.dis.rochecommon.presentation

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Principle of LiveData is very similar to Observable pattern. We have observers (lifecycleOwner)
 * that observs a LiveData instance, whenever the value of said instance changes, all active observers
 * will be notified. Principle of SingleLiveData is to limit the events to 1 per consumer.
 * When multiple observers are observing this LiveData, only 1 observer will consume each new event
 */
class SingleLiveEvent<T> : MutableLiveData<T>() {
    private val mPending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        // Observe the internal MutableLiveData
        super.observe(owner, Observer {
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(it)
            }
        })
    }

    @MainThread
    override fun setValue(t: T?) {
        mPending.set(true)
        super.setValue(t)
    }
}