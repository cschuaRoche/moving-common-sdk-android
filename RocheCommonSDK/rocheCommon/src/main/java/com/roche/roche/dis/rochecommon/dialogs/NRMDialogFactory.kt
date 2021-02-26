package com.roche.roche.dis.rochecommon.dialogs

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.annotation.StringRes
import com.roche.roche.dis.rochecommon.R

object NRMDialogFactory {

    fun showLogoutConfirmationDialog(
        context: Context,
        onButtonClickListener: GenericDialog.OnButtonClickListener
    ) {
        GenericDialog(context)
            .setDialogDescription(R.string.dialog_logout_description)
            .setDialogTitle(R.string.dialog_label_logout)
            .setLeftButton(R.string.dialog_cancel_label)
            .setRightButton(R.string.dialog_label_logout)
            .setButtonClickListener(onButtonClickListener).show()
    }

    fun showDeleteAccountConfirmationDialog(
        context: Context,
        onButtonClickListener: GenericDialog.OnButtonClickListener
    ) {
        GenericDialog(context)
            .setDialogDescription(R.string.dialog_delete_account_description)
            .setDialogTitle(R.string.dialog_label_delete_account)
            .setLeftButton(R.string.dialog_cancel_label)
            .setRightButton(R.string.dialog_button_confirm)
            .setButtonClickListener(onButtonClickListener).show()
    }

    fun showYesOrNo(
        title: String,
        context: Context,
        onButtonClickListener: GenericDialog.OnButtonClickListener
    ) {
        GenericDialog(context)
            .setDialogTitle(title)
            .setLeftButton(R.string.dialog_button_no)
            .setRightButton(R.string.dialog_button_yes)
            .setButtonClickListener(onButtonClickListener).show()
    }

    fun showCancelOrSettings(
        title: String,
        description: String,
        context: Context,
    ) {
        GenericDialog(context)
            .setDialogDescription(description)
            .setDialogTitle(title)
            .setLeftButton(R.string.cancel)
            .setRightButton(R.string.settings)
            .setButtonClickListener(object : GenericDialog.OnButtonClickListener {
                override fun onLeftButtonClick(dialog: NRMDialog<*>) {
                    dialog.dismiss()
                }

                override fun onRightButtonClick(dialog: NRMDialog<*>) {
                    val enrollIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        Intent(Settings.ACTION_FINGERPRINT_ENROLL)
                    } else {
                        Intent(Settings.ACTION_SECURITY_SETTINGS)
                    }
                    context.startActivity(enrollIntent)
                    dialog.dismiss()
                }
            }).show()
    }

    fun showNetworkErrorDialog(context: Context, onButtonClicked: (() -> Unit)? = null) {
        GenericDialog(context)
            .setDialogDescription(R.string.network_error_message)
            .setShowCloseButton(false)
            .setDialogTitle(R.string.network_error_title)
            .setCenterButton(R.string.dialog_button_okay)
            .setButtonClickListener(object : GenericDialog.OnButtonClickListener {
                override fun onCenterButtonClick(dialog: NRMDialog<*>) {
                    dialog.cancel()
                    onButtonClicked?.invoke()
                }

                override fun onCloseButtonClick(dialog: NRMDialog<*>) {
                    dialog.cancel()
                    onButtonClicked?.invoke()
                }
            })
            .show()
    }

    fun showNetworkErrorShorterDialog(context: Context, onButtonClicked: (() -> Unit)? = null) {
        val dialog = getNetworkErrorShorterDialog(context, onButtonClicked)
        dialog.show()
    }

    fun showNonCancellableNetworkErrorDialog(context: Context, onButtonClicked: (() -> Unit)? = null) {
        val dialog = getNetworkErrorShorterDialog(context, onButtonClicked)
        dialog.setCancelable(false)
        dialog.show()
    }

    private fun getNetworkErrorShorterDialog(context: Context, onButtonClicked: (() -> Unit)? = null): GenericDialog {
        val dialog = GenericDialog(context)
        dialog.setDialogDescription(R.string.check_your_internet_connection_error_message)
        dialog.setShowCloseButton(false)
        dialog.setDialogTitle(R.string.network_error_title)
        dialog.setCenterButton(R.string.dialog_button_okay)
        if (onButtonClicked != null) {
            dialog.setOnButtonClicked(onButtonClicked)
        }
        return dialog
    }

    fun showGenericErrorDialog(
        context: Context,
        title: String,
        errorMessage: String,
        onButtonClicked: (() -> Unit)
    ) {
        (getGenericDialog(context, title, errorMessage, true) as GenericDialog)
            .setOnButtonClicked(onButtonClicked)
            .show()
    }

    fun showConfigurableGenericErrorDialog(
        context: Context,
        title: String,
        errorMessage: String,
        cancelabel: Boolean,
        onButtonClicked: (() -> Unit)
    ) {
        (getGenericDialog(context, title, errorMessage, cancelabel) as GenericDialog)
            .setOnButtonClicked(onButtonClicked)
            .show()
    }

    fun showGenericErrorDialog(context: Context, title: String, errorMessage: String) {
        getGenericDialog(context, title, errorMessage, true).show()
    }

    private fun getGenericDialog(
        context: Context,
        title: String,
        message: String,
        cancelabel: Boolean
    ) = GenericDialog(context)
        .setDialogDescription(message)
        .setShowCloseButton(false)
        .setDialogTitle(title)
        .setDialogTitle(title)
        .setCenterButton(R.string.dialog_button_okay)
        .configureCancelable(cancelabel)


    private fun GenericDialog.setOnButtonClicked(onButtonClicked: (() -> Unit)): GenericDialog {
        setButtonClickListener(object : GenericDialog.OnButtonClickListener {
            override fun onCenterButtonClick(dialog: NRMDialog<*>) {
                super.onCenterButtonClick(dialog)
                onButtonClicked.invoke()
                dialog.dismiss()
            }

            override fun onCloseButtonClick(dialog: NRMDialog<*>) {
                super.onCloseButtonClick(dialog)
                onButtonClicked.invoke()
                dialog.dismiss()
            }
        })
        return this
    }

    fun showServerErrorDialog(
        context: Context,
        description: String? = null,
        onButtonClicked: (() -> Unit)? = null
    ) {
        GenericDialog(context)
            .setDialogDescription(
                if (description.isNullOrEmpty()) {
                    context.getString(R.string.server_error_desc)
                } else {
                    description
                }
            )
            .setShowCloseButton(false)
            .setDialogTitle(R.string.server_error_title)
            .setCenterButton(R.string.dialog_button_okay)
            .setButtonClickListener(object : GenericDialog.OnButtonClickListener {
                override fun onCenterButtonClick(dialog: NRMDialog<*>) {
                    dialog.cancel()
                    onButtonClicked?.invoke()
                }

                override fun onCloseButtonClick(dialog: NRMDialog<*>) {
                    dialog.cancel()
                    onButtonClicked?.invoke()
                }
            })
            .show()
    }

    private fun showErrorDialog(
        context: Context,
        @StringRes title: Int,
        @StringRes descriptionRes: Int,
        @StringRes centerBtnText: Int,
        showCloseButton: Boolean,
        onButtonClicked: (() -> Unit)? = null,
        onCloseButtonClicked: (() -> Unit)? = null
    ) {
        showErrorDialog(
            context,
            title,
            context.getString(descriptionRes),
            centerBtnText,
            showCloseButton,
            onButtonClickListener = object : GenericDialog.OnButtonClickListener {
                override fun onCenterButtonClick(dialog: NRMDialog<*>) {
                    dialog.cancel()
                    onButtonClicked?.invoke()
                }

                override fun onCloseButtonClick(dialog: NRMDialog<*>) {
                    onCloseButtonClicked?.invoke()
                }
            })
    }

    fun showErrorDialog(
        context: Context,
        @StringRes title: Int,
        descriptionText: String,
        @StringRes centerBtnText: Int,
        showCloseButton: Boolean,
        onCloseButtonClicked: (() -> Unit)? = null,
        onButtonClickListener: GenericDialog.OnButtonClickListener = object :
            GenericDialog.OnButtonClickListener {
            override fun onCenterButtonClick(dialog: NRMDialog<*>) {
                dialog.closeDialog()
            }

            override fun onCloseButtonClick(dialog: NRMDialog<*>) {
                onCloseButtonClicked?.invoke()
            }
        }
    ) {
        GenericDialog(context)
            .setDialogDescription(descriptionText)
            .setShowCloseButton(showCloseButton)
            .setDialogTitle(title)
            .setCenterButton(centerBtnText)
            .setButtonClickListener(onButtonClickListener)
            .show()
    }

    fun showRootedDeviceDialog(context: Context, onButtonClicked: (() -> Unit)) {
        val dialog = GenericDialog(context)
            .setDialogDescription(R.string.rooted_device_dialog_desc)
            .setShowCloseButton(false)
            .setOnButtonClicked(onButtonClicked)
            .setDialogTitle(R.string.rooted_device_dialog_title)
            .setCenterButton(R.string.dialog_button_ok)
        dialog.show()
        dialog.setCancelable(false)
    }


    fun showInvalidLicenseDialog(context: Context, onButtonClicked: (() -> Unit)) {
        val dialog = GenericDialog(context)
            .setDialogDescription(R.string.invalid_license_dialog_desc)
            .setShowCloseButton(false)
            .setOnButtonClicked(onButtonClicked)
            .setDialogTitle(R.string.invalid_license_dialog_title)
            .setCenterButton(R.string.dialog_button_ok)
        dialog.show()
        dialog.setCancelable(false)
    }
}
