package com.roche.ssg.rochecommon.dialogs

import android.app.Dialog

interface CloseDialogListener {
    fun addCloseEventListener(dialog: Dialog)
}
