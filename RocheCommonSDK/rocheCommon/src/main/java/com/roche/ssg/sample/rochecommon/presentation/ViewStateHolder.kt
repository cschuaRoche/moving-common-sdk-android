package com.roche.ssg.sample.rochecommon.presentation

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class ViewStateHolderImpl<T> : ViewStateHolder<T> {
    private val _viewState = MutableLiveData<T>()

    override fun updateState(stateCopy: (T?) -> T) {
        val oldState = _viewState.value
        _viewState.value =
            stateCopy(oldState)//Figure out a way around nullability and without forcing default value
    }

    override val viewState: LiveData<T>
        get() = _viewState
}

fun <T> Fragment.observeState(viewModel: ViewStateHolder<T>, onUpdate: (T) -> Unit) {
    viewModel.viewState.observe(viewLifecycleOwner, Observer {
        onUpdate(it)
    })
}

interface ViewStateHolder<T> {
    fun updateState(stateCopy: (T?) -> T)
    val viewState: LiveData<T>
}