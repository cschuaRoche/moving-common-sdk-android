package com.roche.ssg.sample.splitio.data

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.roche.ssg.sample.BR

class SelectedItem : BaseObservable() {

    @get:Bindable
    var position: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.position)
        }
}