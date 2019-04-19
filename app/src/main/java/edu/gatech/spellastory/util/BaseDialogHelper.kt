package edu.gatech.spellastory.util

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AlertDialog
import android.view.View

/**
 * CREDIT: https://android.jlelse.eu/android-custom-alert-dialogs-kotlin-extension-functions-kotlin-higher-order-functions-life-682305c5322e
 */
abstract class BaseDialogHelper {

    abstract val dialogView: View
    abstract val builder: AlertDialog.Builder

    //  required bools
    open var cancelable: Boolean = true
    open var isBackGroundTransparent: Boolean = true

    //  dialog
    open var dialog: AlertDialog? = null

    //  dialog create
    open fun create(): AlertDialog {
        dialog = builder
            .setCancelable(cancelable)
            .create()

        //  very much needed for customised dialogs
        if (isBackGroundTransparent)
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog!!
    }

    //  cancel listener
    open fun onCancelListener(func: () -> Unit): AlertDialog.Builder? =
        builder.setOnCancelListener {
            func()
        }
}