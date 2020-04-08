package com.example.vinid_icecreams.utils

import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.view.WindowManager
import java.text.SimpleDateFormat
import java.util.*

class CommonUtils {

    companion object {
        var instace = CommonUtils()
        var token = ""
        const val TOKEN = "token"
        const val PREF_SAVE_NAME = "IceCream data"
    }


    fun getDateNow(dateStyle: String?): String? {
        try {
            val dateFormat =
                SimpleDateFormat(dateStyle, Locale.getDefault())
            dateFormat.timeZone = TimeZone.getDefault()
            return dateFormat.format(Date(System.currentTimeMillis()))
        } catch (e: Exception) {

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

    fun checkPermission(context: Context, permission: String): Boolean {
        val res: Int = context.checkCallingOrSelfPermission(permission)
        return (res == PackageManager.PERMISSION_GRANTED)
    }

    fun isConnectToNetwork(context: Context?): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    fun calculateAverage(marks: List<Int>): Float {
        var sum = 0F
        if (marks.isNotEmpty()) {
            for (mark in marks) {
                sum += mark
            }
            return sum / marks.size
        }
        return sum
    }
}
