package com.example.vinid_icecreams.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.view.WindowManager
import com.developer.kalert.KAlertDialog
import com.example.vinid_icecreams.model.Order
import com.example.vinid_icecreams.model.Store
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt


class CommonUtils {
    val TAG = "Hungpld"


    companion object {
        var instace = CommonUtils()
        var mListOrder: ArrayList<Order>? = ArrayList()
        var mTotalPayment = 0
        var mSelectedStore: Store? = null
        var mAmount = 0
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

    fun setOrderToList(order: Order) {
        if (mListOrder?.size!! > 0) {
            val i = mListOrder!!.size - 1
            if (order.iceCream.id == mListOrder!![i].iceCream.id) {
                mListOrder!![i].amount += 1
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
    fun calculationByDistance(
        startLatitude: Double,
        startLongitude: Double,
        endLatitude: Double,
        endLongitude: Double
    ): Double {
        val radius = 6371 // radius of earth in Km
        val lat1: Double = startLatitude
        val lat2: Double = endLatitude
        val lon1: Double = startLongitude
        val lon2: Double = endLongitude
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = (sin(dLat / 2) * sin(dLat / 2)
                + (cos(Math.toRadians(lat1))
                * cos(Math.toRadians(lat2)) * sin(dLon / 2)
                * sin(dLon / 2)))
        val c = 2 * asin(sqrt(a))
        val valueResult = radius * c
        val km = valueResult / 1
        val newFormat = DecimalFormat("####")
        val kmInDec: Int = Integer.valueOf(newFormat.format(km))
        val meter = valueResult % 1000
        val meterInDec: Int = Integer.valueOf(newFormat.format(meter))
        Log.i(
            "Radius Value", "" + valueResult + "   KM  " + kmInDec
                    + " Meter   " + meterInDec
        )
        return valueResult
    }

    fun sortList(mListData: ArrayList<Store>): ArrayList<Store> {
        return ArrayList(mListData.sortedWith(compareBy { it.range }))
    }

    fun showSomeThingWentWrong(activity: Activity?) {
        KAlertDialog(activity, KAlertDialog.ERROR_TYPE)
            .setTitleText("Some thing went wrong")
            .show()
    }


    fun saveStoreSelected(store: Store) {
        mSelectedStore = store
    }

    fun savePrefContent(context: Context?, key: String, value: String?) {
        val editor = context?.getSharedPreferences(
            PREF_SAVE_NAME,
            Context.MODE_PRIVATE
        )?.edit()
        editor?.putString(key, value)
        editor?.apply()
    }

    fun getPrefContent(context: Context?, key: String): String? {
        var result = ""
        val sharedPreferences = context?.getSharedPreferences(PREF_SAVE_NAME, Context.MODE_PRIVATE)
        if (sharedPreferences != null) {
            result = sharedPreferences.getString(key, "")!!
        }
        return result
    }

    fun Context.openPermissionSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }

}
