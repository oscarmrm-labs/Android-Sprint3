package com.qualentum.sprint3.common.ui

import android.app.AlertDialog
import android.content.Context
import com.qualentum.sprint3.R

class CommonError {
    companion object {
        fun show(context: Context) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.error_title)
            builder.setMessage(R.string.error_message)
            builder.setPositiveButton(R.string.error_confirm) { dialog, _ ->
                dialog.dismiss()
            }
            builder.setIcon(R.drawable.error_svg)
            builder.setCancelable(false)
            val alertDialog = builder.create()
            alertDialog.show()
        }
    }
}
