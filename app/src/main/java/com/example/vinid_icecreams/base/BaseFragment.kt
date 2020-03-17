package com.example.vinid_icecreams.base

import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.Observer
import com.developer.kalert.KAlertDialog
import com.example.vinid_icecreams.utils.ProgressLoading
import dagger.android.support.DaggerFragment
import timber.log.Timber

abstract class BaseFragment<T : BaseViewModel> : DaggerFragment() {

    abstract fun provideViewModel(): T

    var messageSuccess = ""
    var messageFail = ""


    fun observeMessage() {
        val viewModel = provideViewModel()
        viewModel.messageSuccess.observe(viewLifecycleOwner, Observer {
            messageSuccess = it
        })

        viewModel.messageFail.observe(viewLifecycleOwner, Observer {
            showDiaLogFail("Error !!!",it)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it) {
                ProgressLoading.show(requireContext())
            } else {
                ProgressLoading.dismiss()
            }
        })
    }

    fun isConnectToNetwork(context: Context?): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    fun showNoConnection(callback: ConnectionListener) {
        val dialog = KAlertDialog(activity, KAlertDialog.ERROR_TYPE)
            .setTitleText("Missing connection ")
            .setContentText("Check your connection")
            .setConfirmClickListener {
                it.dismiss()
                callback.onButtonOkConnectionClick()
            }
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    fun showNoConnection() {
        val dialog = KAlertDialog(activity, KAlertDialog.ERROR_TYPE)
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
        KAlertDialog(context, KAlertDialog.SUCCESS_TYPE)
            .setTitleText(title)
            .setContentText(messageSuccess)
            .show()
    }

    fun showDiaLogFail(
        title: String,
        messageFail: String
    ) {
        ProgressLoading.dismiss()
        KAlertDialog(context, KAlertDialog.ERROR_TYPE)
            .setTitleText(title)
            .setContentText(messageFail)
            .show()
    }
}

interface ConnectionListener {
    fun onButtonOkConnectionClick()
}