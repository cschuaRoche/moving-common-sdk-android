package com.roche.ssg.rochecommon.dialogs

import android.content.Context
import android.text.Annotation
import android.text.SpannableString
import android.text.Spanned
import android.text.SpannedString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.method.ScrollingMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.roche.ssg.rochecommon.R
import com.roche.ssg.rochecommon.databinding.ViewGenericDialogBinding

/**
 * This is for showing custom dialog such as Confirmation, YesOrNo, Okay, etc.
 *
 * To change the style of the GenericDialog, create a refs.xml and override the styles i.e:
 * <item name="GenericDialog_Title" type="style">@style/MyCustomStyle</item>
 */
@Suppress("unused")
class GenericDialog(
    context: Context,
    themeRes: Int = R.style.GenericDialog,
    layoutRes: Int = R.layout.view_generic_dialog
) : RocheDialog<ViewGenericDialogBinding>(context, themeRes, layoutRes) {

    init {
        setUpListeners()
        addCloseEventListener(this)
    }

    override fun setUpListeners() {
        binding.genericDialog = this
        binding.genericDialogClickListener = object : OnButtonClickListener {
            override fun onCenterButtonClick(dialog: RocheDialog<*>) {
                dialog.closeDialog()
            }
        }
        super.setContentView(binding.root)
    }

    override fun setButtonClickListener(buttonClickListener: OnButtonClickListener): GenericDialog {
        binding.genericDialogClickListener = buttonClickListener
        binding.executePendingBindings()
        return this
    }

    fun setDialogDescription(@StringRes text: Int): GenericDialog {
        setText(binding.dialogDescription, text)
        binding.dialogDescription.movementMethod = ScrollingMovementMethod()
        return this
    }

    fun setDialogDescription(text: String): GenericDialog {
        setText(binding.dialogDescription, text)
        binding.dialogDescription.movementMethod = ScrollingMovementMethod()
        return this
    }

    fun setDialogDescription(text: CharSequence): GenericDialog {
        binding.dialogDescription.text = text
        binding.dialogDescription.movementMethod = ScrollingMovementMethod()
        return this
    }

    fun setDescriptionWithHyperLinks(
        @StringRes descriptionRes: Int,
        hyperLinksMap: Map<String, String>,
        buttonClickListener: OnButtonClickListener
    ): GenericDialog {
        val description = SpannedString(context.getText(descriptionRes))
        val spannableString = SpannableString(description)

        val annotations = description.getSpans(0, description.length, Annotation::class.java)

        hyperLinksMap.forEach { (key, value) ->
            val clickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    buttonClickListener.onDescriptionLinkClick(
                        dialog = this@GenericDialog,
                        hyperLink = value
                    )
                }

                override fun updateDrawState(ds: TextPaint) {
                    ds.isUnderlineText = true
                }
            }
            annotations?.find { it.value == key }?.let {
                spannableString.apply {
                    setSpan(
                        clickableSpan,
                        description.getSpanStart(it),
                        description.getSpanEnd(it),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    setSpan(
                        ForegroundColorSpan(
                            ContextCompat.getColor(context, R.color.colorDialogButtonBlue)
                        ),
                        description.getSpanStart(it),
                        description.getSpanEnd(it),
                        0
                    )
                }
            }
        }

        binding.dialogDescription.movementMethod = LinkMovementMethod.getInstance()
        binding.dialogDescription.text = spannableString
        return this
    }

    fun setDialogSubtitle(text: String): GenericDialog {
        setText(binding.dialogSubtitle, text)
        return this
    }

    fun setDialogSubtitle(@StringRes text: Int): GenericDialog {
        setText(binding.dialogSubtitle, text)
        return this
    }

    fun setDialogResultValue(text: String): GenericDialog {
        setText(binding.dialogResultTitle, text)
        return this
    }

    fun setDialogManualInputValue(text: String): GenericDialog {
        setText(binding.dialogManualInputValue, text)
        return this
    }

    fun setIcon(@DrawableRes icon: Int): GenericDialog {
        with(binding.dialogPopupImage) {
            setImageResource(icon)
            visibility = View.VISIBLE
        }
        return this
    }

    fun setShowCloseButton(show: Boolean): GenericDialog {
        binding.dialogCloseIc.visibility = if (show) View.VISIBLE else View.INVISIBLE
        return this
    }

    override fun closeDialog() {
        cancel()
        binding.genericDialogClickListener?.onCloseButtonClick(this)
    }

    interface OnButtonClickListener {
        fun onLeftButtonClick(dialog: RocheDialog<*>) {
            //do nothing by default
        }

        fun onRightButtonClick(dialog: RocheDialog<*>) {
            //do nothing by default
        }

        fun onCenterButtonClick(dialog: RocheDialog<*>) {
            //do nothing by default
        }

        fun onCloseButtonClick(dialog: RocheDialog<*>) {
            //do nothing by default
        }

        fun onDescriptionLinkClick(dialog: RocheDialog<*>, hyperLink: String) {
            // do nothing by default
        }
    }
}