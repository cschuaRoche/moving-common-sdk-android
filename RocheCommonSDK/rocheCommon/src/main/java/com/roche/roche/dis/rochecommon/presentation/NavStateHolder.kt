package com.roche.roche.dis.rochecommon.presentation

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

class NavStateHolderImpl : NavStateHolder {
    private val _navState = SingleLiveEvent<NavEvent>()
    override fun newNavState(navState: NavEvent) {
        _navState.value = navState
    }

    override val navState: LiveData<NavEvent>
        get() = _navState
}

interface NavStateHolder {
    fun newNavState(navState: NavEvent)
    val navState: LiveData<NavEvent>
}

fun Fragment.observeNavState(viewModel: NavStateHolder, onUpdate: (NavEvent) -> Unit) {
    viewModel.navState.observe(viewLifecycleOwner, Observer {
        onUpdate(it)
    })
}

open class NavEvent