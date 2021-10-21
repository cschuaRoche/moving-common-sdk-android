package com.roche.ssg.sample.rochecommon.dialogs

import android.app.Dialog

interface CloseDialogListener {
    fun addCloseEventListener(dialog: Dialog)
}
