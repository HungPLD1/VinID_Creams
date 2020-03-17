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

    fun observeLoading() {
        val viewModel = provideViewModel()
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

    fun showNoConnection(callback: DialogClickListener) {
        val dialog = KAlertDialog(requireContext(), KAlertDialog.ERROR_TYPE)
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
        val dialog = KAlertDialog(requireContext(), KAlertDialog.ERROR_TYPE)
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

    fun showDiaLogFail(
        title: String,
        messageFail: String,
        listener : DialogClickListener
    ) {
        ProgressLoading.dismiss()
        KAlertDialog(requireContext(), KAlertDialog.ERROR_TYPE)
            .setTitleText(title)
            .setContentText(messageFail)
            .setConfirmClickListener {
                it.dismiss()
                listener.onConfirmClickListener() }
            .show()
    }

    companion object{
        const val ERROR  = "ERROR !!!"
    }
}

interface DialogClickListener{
    fun onConfirmClickListener()
    fun onCancelListener()
}