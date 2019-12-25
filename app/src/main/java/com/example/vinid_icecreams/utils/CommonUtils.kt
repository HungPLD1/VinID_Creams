package com.example.vinid_icecreams.utils

import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.view.WindowManager
import java.text.SimpleDateFormat
import java.util.*


class CommonUtils {
    val TAG = "Hungpld"
    val REQUEST_CODE_PEMISSION = 0

    companion object{
        var instace = CommonUtils()
    }



    fun getDateNow(dateStyle: String?): String? {
        try {
            val dateFormat =
                SimpleDateFormat(dateStyle, Locale.getDefault())
            dateFormat.timeZone = TimeZone.getDefault()
            return dateFormat.format(Date(System.currentTimeMillis()))
        } catch (e: Exception) {
            Log.d(TAG,e.toString())
        }
        return null
    }

    // Prevent dialog dismiss when orientation changes
    fun doKeepDialog(dialog: Dialog) {
        if (dialog.window == null) return
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window!!.attributes = lp
    }

    fun checkPermission(context : Context,permission : String): Boolean {
        val res: Int =  context.checkCallingOrSelfPermission(permission)
        return (res == PackageManager.PERMISSION_GRANTED)
    }

}