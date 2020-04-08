package com.example.vinid_icecreams.utils

import android.app.Dialog
import android.content.Context
import android.os.Handler
import android.view.Gravity
import android.view.Window
import com.example.vinid_icecreams.R

object ProgressLoading {
    private var pdLoading: Dialog? = null
    private var isHide = false

    fun dontShow() {
        isHide = true
    }

    private fun isLoading(): Boolean {
        return pdLoading != null && pdLoading!!.isShowing
    }

    fun show(context: Context?) {
        if (!isLoading() && context != null && !isHide) {
            try {
                if (pdLoading == null) {
                    pdLoading = Dialog(context)
                    pdLoading!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    pdLoading!!.setContentView(R.layout.layout_loading)
                    if (pdLoading!!.window != null) {
                        pdLoading!!.window!!.setBackgroundDrawableResource(R.drawable.bg_transparent)
                    }
                    pdLoading!!.setCanceledOnTouchOutside(false)
                    pdLoading!!.window!!.setGravity(Gravity.CENTER)
                    pdLoading!!.setCancelable(false)
                }
                pdLoading!!.show()
                CommonUtils.instace.doKeepDialog(pdLoading!!)
            } catch (ignored: Exception) {
                ignored.printStackTrace()
            }
        }
        isHide = false
    }

    fun dismiss() {
            Handler().postDelayed({
                try {
                    if (pdLoading != null && pdLoading!!.isShowing) {
                        pdLoading!!.dismiss()
                        pdLoading = null
                    }
                } catch (ignored: java.lang.Exception) { //ignored.printStackTrace();
                }
            }, 800)
    }

}