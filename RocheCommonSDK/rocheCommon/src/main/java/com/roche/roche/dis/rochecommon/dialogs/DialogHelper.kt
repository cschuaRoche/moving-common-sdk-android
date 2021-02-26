package com.roche.roche.dis.rochecommon.dialogs

import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

import androidx.localbroadcastmanager.content.LocalBroadcastManager

const val CLOSE_EVENT = "CLOSE_ALL_DIALOGS"

class DialogHelper : CloseDialogListener {

    companion object {
        /**
         * Sends a close broadcast event to all dialogs
         */
        fun closeAllDialogs(context: Context) {
            LocalBroadcastManager.getInstance(context).sendBroadcast(Intent(CLOSE_EVENT))
        }
    }

    /**
     * Adds a close event broadcast receiver to the dialog
     */
    override fun addCloseEventListener(dialog: Dialog) {
        val mBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                if (intent.action == CLOSE_EVENT && dialog.isShowing) {
                    dialog.dismiss()
                }
            }
        }
        dialog.setOnShowListener {
            val mIntentFilter = IntentFilter()
            mIntentFilter.addAction(CLOSE_EVENT)
            LocalBroadcastManager.getInstance(dialog.context)
                .registerReceiver(mBroadcastReceiver, mIntentFilter)
        }

        dialog.setOnDismissListener {
            LocalBroadcastManager.getInstance(dialog.context).unregisterReceiver(mBroadcastReceiver)
        }

        dialog.setOnCancelListener {
            LocalBroadcastManager.getInstance(dialog.context).unregisterReceiver(mBroadcastReceiver)
        }
    }
}
