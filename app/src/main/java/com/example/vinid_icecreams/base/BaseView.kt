package com.example.vinid_icecreams.base

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import com.developer.kalert.KAlertDialog
import com.example.vinid_icecreams.utils.ProgressLoading

interface BaseView {
    fun providerContext(): Context?

    fun provideRootView(): View?

    fun setUpUI() {
        provideRootView()?.setOnClickListener(null)
    }

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
        title: String?,
        messageSuccess: String?
    ) {
        ProgressLoading.dismiss()
        KAlertDialog(providerContext(), KAlertDialog.SUCCESS_TYPE)
            .setTitleText(title)
            .setContentText(messageSuccess)
            .show()
    }

    fun showDiaLogFailed(
        title: String?,
        messageFail: String?
    ) {
        ProgressLoading.dismiss()
        KAlertDialog(providerContext(), KAlertDialog.ERROR_TYPE)
            .setTitleText(title)
            .setContentText(messageFail)
            .show()
    }

    fun showDiaLogFailed(
        title: String,
        messageFail: String,
        listener: DialogClickListener
    ) {
        ProgressLoading.dismiss()
        KAlertDialog(providerContext(), KAlertDialog.ERROR_TYPE)
            .setTitleText(title)
            .setContentText(messageFail)
            .setConfirmClickListener {
                it.dismiss()
                listener.onConfirmClickListener()
            }
            .show()
    }

    fun showDialogWarning(
        title: String?,
        messageWarning: String?
    ) {
        ProgressLoading.dismiss()
        KAlertDialog(providerContext(), KAlertDialog.WARNING_TYPE)
            .setTitleText(title)
            .setContentText(messageWarning)
            .show()
    }

    fun showSomeThingWentWrong(activity: Activity?) {
        KAlertDialog(activity, KAlertDialog.ERROR_TYPE)
            .setTitleText("Some thing went wrong")
            .show()
    }
}

interface DialogClickListener {
    fun onConfirmClickListener()
    fun onCancelListener()
}