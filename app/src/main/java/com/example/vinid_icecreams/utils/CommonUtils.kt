package com.example.vinid_icecreams.utils

import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.util.Log
import android.view.WindowManager
import com.example.vinid_icecreams.model.Order
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CommonUtils {
    val TAG = "Hungpld"
    val REQUEST_CODE_PEMISSION = 0


    companion object {
        var instace = CommonUtils()
        var mListOrder: ArrayList<Order>? = ArrayList()
        var mTotalPayment = 0
    }


    fun getDateNow(dateStyle: String?): String? {
        try {
            val dateFormat =
                SimpleDateFormat(dateStyle, Locale.getDefault())
            dateFormat.timeZone = TimeZone.getDefault()
            return dateFormat.format(Date(System.currentTimeMillis()))
        } catch (e: Exception) {
            Log.d(TAG, e.toString())
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

    fun isConnectToNetwork(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    fun calculateAverage(marks: List<Float>): Float {
        var sum = 0F
        if (marks.isNotEmpty()) {
            for (mark in marks) {
                sum += mark
            }
            return sum / marks.size
        }
        return sum
    }

    fun setOrderToList(order: Order) {
        if (mListOrder?.size!! > 0) {
            val i = mListOrder!!.size -1
            if (order.mIceCream.id == mListOrder!![i].mIceCream.id) {
                mListOrder!![i].mAmount += 1
            } else {
                mListOrder?.add(order)
            }

        } else {
            mListOrder?.add(order)
        }
    }

    fun getOrderList(): ArrayList<Order>? {
        return mListOrder
    }

    fun setTotalPayment(total: Int) {
        mTotalPayment = total
    }

    fun getTotalPayment(): Int {
        return mTotalPayment
    }

    /*fun get rage of two point*/
    fun CalculationByDistance(
        startLatitude: Double,
        startLongitude: Double,
        endLatitude: Double,
        endLongitude: Double
    ): Double {
        val Radius = 6371 // radius of earth in Km
        val lat1: Double = startLatitude
        val lat2: Double = endLatitude
        val lon1: Double = startLongitude
        val lon2: Double = endLongitude
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = (Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + (Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2)))
        val c = 2 * Math.asin(Math.sqrt(a))
        val valueResult = Radius * c
        val km = valueResult / 1
        val newFormat = DecimalFormat("####")
        val kmInDec: Int = Integer.valueOf(newFormat.format(km))
        val meter = valueResult % 1000
        val meterInDec: Int = Integer.valueOf(newFormat.format(meter))
        Log.i(
            "Radius Value", "" + valueResult + "   KM  " + kmInDec
                    + " Meter   " + meterInDec
        )
        return Radius * c
    }
}