package com.example.vinid_icecreams.base

import android.content.Context
import android.net.ConnectivityManager
import com.developer.kalert.KAlertDialog
import com.example.vinid_icecreams.utils.ProgressLoading

interface BaseView {
    fun providerContext() : Context?

    fun setUpUI()

    fun setupViewModel()

    fun isConnectToNetwork(context: Context?): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    fun showNoConnection(callback: DialogClickListener) {
        val dialog = KAlertDialog(providerContext(), KAlertDialog.ERROR_TYPE)
            .setTitleText("Missing connection ")
            .setContentText("Check your connection")
            .setConfirmClickListener {
                it.dismiss()
                callback.onConfirmClickListener()
            }
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    fun showNoConnection() {
        val dialog = KAlertDialog(providerContext(), KAlertDialog.ERROR_TYPE)
            .setTitleText("Missing connection ")
            .setContentText("Check your connection")
            .setConfirmClickListener {
                it.dismiss()
            }
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    fun showDialogSuccess(
        title: String,
        messageSuccess: String
    ) {
        ProgressLoading.dismiss()
        KAlertDialog(providerContext(), KAlertDialog.SUCCESS_TYPE)
            .setTitleText(title)
            .setContentText(messageSuccess)
            .show()
    }

    fun showDiaLogFail(
        title: String,
        messageFail: String
    ) {
        ProgressLoading.dismiss()
        KAlertDialog(providerContext(), KAlertDialog.ERROR_TYPE)
            .setTitleText(title)
            .setContentText(messageFail)
            .show()
    }

    fun showDiaLogFail(
        title: String,
        messageFail: String,
        listener : DialogClickListener
    ) {
        ProgressLoading.dismiss()
        KAlertDialog(providerContext(), KAlertDialog.ERROR_TYPE)
            .setTitleText(title)
            .setContentText(messageFail)
            .setConfirmClickListener {
                it.dismiss()
                listener.onConfirmClickListener() }
            .show()
    }
}

interface DialogClickListener{
    fun onConfirmClickListener()
    fun onCancelListener()
}