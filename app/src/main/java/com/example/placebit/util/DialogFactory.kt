package com.example.placebit.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.example.placebit.R
import kotlinx.android.synthetic.main.dialog_success.*

enum class DialogFactoryType {
    SUCCESS, ERROR, INFO
}

class DialogFactory(
    var type: DialogFactoryType, var context: Context,
    var firstButtonListener: View.OnClickListener,
    var secondButtonListener: View.OnClickListener
) {

    private lateinit var dialog: Dialog

    init {
        createBaseDialog(context)
        when (type) {
            DialogFactoryType.SUCCESS -> {
                //dialog.dialogIcon.setImageDrawable(context.getDrawable(R.drawable.ic_dialog_success))
            }
            DialogFactoryType.ERROR -> {
                //dialog.dialogIcon.setImageDrawable(context.getDrawable(R.drawable.ic_dialog_error))
            }
            DialogFactoryType.INFO -> {
                //dialog.textHeader.visibility = View.VISIBLE
                //dialog.dialogIcon.visibility = View.INVISIBLE
            }
        }
    }

    fun setInfo(
        message: String,
        button1: String? = null,
        button2: String? = null,
        removeIcon: Boolean = false
    ): Dialog {
        dialog.dialogText.text = message

        if (button1 != null) {
            dialog.buttonFirst.text = button1
        } else {
            dialog.buttonFirst.visibility = View.GONE
        }

        if (button2 != null) {
            dialog.buttonSecond.text = button2
        } else {
            dialog.buttonSecond.visibility = View.GONE
        }

        if (removeIcon) {
            dialog.dialogIcon.visibility = View.GONE
        }

        return dialog
    }

    fun setInfoWihTitle(
        message: String? = null,
        button1: String? = null,
        button2: String? = null,
        removeIcon: Boolean = false
    ): Dialog {

        message?.let {
            dialog.dialogText.text = message
        }

        if (button1 != null) {
            dialog.buttonFirst.text = button1
        } else {
            dialog.buttonFirst.visibility = View.GONE
        }

        if (button2 != null) {
            dialog.buttonSecond.text = button2
        } else {
            dialog.buttonSecond.visibility = View.GONE
        }

        if (removeIcon) {
            dialog.dialogIcon.visibility = View.GONE
        }

        return dialog
    }

    private fun createBaseDialog(context: Context): Dialog {
        dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_success)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val window = dialog.window
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        dialog.buttonFirst.setOnClickListener(firstButtonListener)
        dialog.buttonSecond.setOnClickListener(secondButtonListener)

        return dialog
    }

}