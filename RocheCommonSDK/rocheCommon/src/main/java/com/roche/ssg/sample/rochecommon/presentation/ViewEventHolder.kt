package com.roche.ssg.sample.rochecommon.presentation

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

class ViewEventHolderImpl : ViewEventHolder {
    private val _viewEvents = SingleLiveEvent<Event>()

    override fun newEvent(navState: Event) {
        _viewEvents.value = navState
    }

    override val viewEvents: LiveData<Event>
        get() = _viewEvents
}

interface ViewEventHolder {
    fun newEvent(event: Event)
    val viewEvents: LiveData<Event>
}

fun Fragment.observeViewEvents(viewModel: ViewEventHolder, onUpdate: (Event) -> Unit) {
    viewModel.viewEvents.observe(viewLifecycleOwner, Observer {
        onUpdate(it)
    })
}

open class Event{
    object ShowNoInternet : Event()
    object ShowSessionExpired : Event()
    object ShowServerUnreachable : Event()
}