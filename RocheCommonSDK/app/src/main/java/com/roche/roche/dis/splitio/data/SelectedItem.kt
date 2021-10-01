package com.roche.roche.dis.splitio.data

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.roche.roche.dis.BR

class SelectedItem : BaseObservable() {

    @get:Bindable
    var position: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.position)
        }
}