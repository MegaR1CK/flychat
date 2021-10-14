package com.hfad.flychat

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import com.google.android.gms.tasks.OnFailureListener

class App : Application() {
    companion object {

        var WAS_AUTH = false
//dsfds
        fun errorAlert(mes: String, context: Context) {
            AlertDialog.Builder(context)
                .setTitle(R.string.error_title)
                .setMessage(mes)
                .setPositiveButton(R.string.ok, null)
                .create()
                .show()
        }
    }
}