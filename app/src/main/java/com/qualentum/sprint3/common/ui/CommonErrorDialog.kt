package com.qualentum.sprint3.common.ui

import android.app.AlertDialog
import android.content.Context
import com.qualentum.sprint3.R

object CommonErrorDialog {
    //fun show(context: Context, title: String, message: String) {
    fun show(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error")
        builder.setMessage("Ha ocurrido un error")
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        builder.setIcon(R.drawable.error_svg)
        builder.setCancelable(false)
        val alertDialog = builder.create()
        alertDialog.show()
    }
}