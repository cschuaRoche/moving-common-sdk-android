package com.roche.roche.dis.rochecommon.dialogs

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.roche.roche.dis.rochecommon.R
import com.roche.roche.dis.rochecommon.utils.fromHtml

abstract class NRMDialog<T : ViewDataBinding>(context: Context, themeRes: Int, layoutRes: Int) :
    Dialog(context, themeRes), CloseDialogListener by DialogHelper() {
    abstract fun closeDialog()
    abstract fun setUpListeners()
    abstract fun setButtonClickListener(buttonClickListener: GenericDialog.OnButtonClickListener): NRMDialog<T>

    var binding: T

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        //init binding & view
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(getContext()),
            layoutRes,
            null,
            true
        )
    }

    fun setOnDismissListener(onDismissListener: DialogInterface.OnDismissListener): NRMDialog<T> {
        super.setOnDismissListener(onDismissListener)
        return this
    }

    /**
     * Sets the font style of all TextViews of this dialog
     * Note: you may also customize this by overriding the GenericDialog styles in the refs.xml
     * @see R.style.GenericDialog_Title
     * @see R.style.GenericDialog_Subtitle
     * @see R.style.GenericDialog_Result_Title
     * @see R.style.GenericDialog_Manual_Input_value
     * @see R.style.GenericDialog_Description
     * @see R.style.GenericDialog_Btn_Left
     * @see R.style.GenericDialog_Btn_Right
     * @see R.style.GenericDialog_Btn_Single
     */
    fun setTextFont(typeface: Typeface): NRMDialog<T>  {
        val dialogTitle: TextView = findViewById(R.id.dialog_title) ?: throw getResourceException("dialog_title")
        dialogTitle.typeface = typeface
        val dialogDescription: TextView = findViewById(R.id.dialog_description) ?: throw getResourceException("dialog_description")
        dialogDescription.typeface = typeface
        val dialogSubtitle: TextView = findViewById(R.id.dialog_subtitle) ?: throw getResourceException("dialog_subtitle")
        dialogSubtitle.typeface = typeface
        val dialogBtnLeft: TextView = findViewById(R.id.dialog_btn_left) ?: throw getResourceException("dialog_btn_left")
        dialogBtnLeft.typeface = typeface
        val dialogBtnRight: TextView = findViewById(R.id.dialog_btn_right) ?: throw getResourceException("dialog_btn_right")
        dialogBtnRight.typeface = typeface
        val dialogBtnSingle: TextView = findViewById(R.id.dialog_btn_single) ?: throw getResourceException("dialog_btn_single")
        dialogBtnSingle.typeface = typeface
        return this
    }

    fun setColorScheme(color: Int): NRMDialog<T> {
        val dialogTitle: TextView = findViewById(R.id.dialog_title) ?: throw Exception("Missing dialog_title in dialog layout")
        dialogTitle.setTextColor(color)
        val dialogBtnRight: TextView = findViewById(R.id.dialog_btn_right) ?: throw getResourceException("dialog_btn_right")
        dialogBtnRight.setBackgroundColor(color)
        return this
    }

    fun setLeftButton(@StringRes text: Int): NRMDialog<T> {
        val dialogBtnLeft: TextView = findViewById(R.id.dialog_btn_left) ?: throw getResourceException("dialog_btn_left")
        dialogBtnLeft.setText(text)
        return this
    }

    fun setLeftButton(text: String): NRMDialog<T> {
        val dialogBtnLeft: TextView = findViewById(R.id.dialog_btn_left) ?: throw getResourceException("dialog_btn_left")
        dialogBtnLeft.text = text
        return this
    }

    fun setRightButton(@StringRes text: Int): NRMDialog<T> {
        val dialogBtnRight: TextView = findViewById(R.id.dialog_btn_right) ?: throw getResourceException("dialog_btn_right")
        dialogBtnRight.setText(text)
        return this
    }

    fun setRightButton(text: String): NRMDialog<T> {
        val dialogBtnRight: TextView = findViewById(R.id.dialog_btn_right) ?: throw getResourceException("dialog_btn_right")
        dialogBtnRight.text = text
        return this
    }

    fun setCenterButton(@StringRes text: Int): NRMDialog<T> {
        val dialogBtnSingle: TextView = findViewById(R.id.dialog_btn_single) ?: throw getResourceException("dialog_btn_single")
        dialogBtnSingle.setText(text)
        return this
    }

    fun setCenterButton(text: String): NRMDialog<T> {
        val dialogBtnSingle: TextView = findViewById(R.id.dialog_btn_single) ?: throw getResourceException("dialog_btn_single")
        dialogBtnSingle.text = text
        return this
    }

    fun setDialogTitle(@StringRes text: Int): NRMDialog<T> {
        val dialogTitle: TextView = findViewById(R.id.dialog_title) ?: throw getResourceException("dialog_title")
        dialogTitle.setText(text)
        return this
    }

    fun setDialogTitle(text: String): NRMDialog<T> {
        val dialogTitle: TextView = findViewById(R.id.dialog_title) ?: throw getResourceException("dialog_title")
        dialogTitle.text = text
        return this
    }

    fun configureCancelable(cancelable: Boolean): NRMDialog<T> {
        setCancelable(cancelable)
        return this
    }

    fun setText(view: TextView, title: Int) {
        view.visibility = View.VISIBLE
        view.setText(title)
        view.text = view.text.toString().fromHtml()
    }

    fun setText(view: TextView, text: String) {
        view.visibility = View.VISIBLE
        view.text = text.fromHtml()
    }

    private fun getResourceException(name: String): Exception {
        return Exception("Missing R.id.$name in dialog layout")
    }
}