package com.example.vinid_icecreams.base

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.developer.kalert.KAlertDialog
import com.example.vinid_icecreams.di.viewModelModule.ViewModelFactory
import com.example.vinid_icecreams.utils.ProgressLoading
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment< T : BaseViewModel> @Inject constructor(
) : DaggerFragment() {
    @Inject
    lateinit var viewmodelFactory : ViewModelFactory

    private val viewModel: T by lazy {
        ViewModelProvider(this,viewmodelFactory).get(T::class.java)
    }

    lateinit var messgaeSuccess : String
    lateinit var messageFail : String



    fun observeMessage(){
        viewModel.messageSuccess.observe(viewLifecycleOwner, Observer {
            messgaeSuccess = it
        })

        viewModel.messageFail.observe(viewLifecycleOwner, Observer {
            messageFail = it
        })
    }

    fun showNoConnection(callback : ConnectionListener){
        val dialog = KAlertDialog(activity, KAlertDialog.ERROR_TYPE)
            .setTitleText("Missing connection ")
            .setContentText("Check your connection")
            .setConfirmClickListener{
                it.dismiss()
                callback.onButtonClick()
            }
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    fun showNoConnection(){
        val dialog = KAlertDialog(activity, KAlertDialog.ERROR_TYPE)
            .setTitleText("Missing connection ")
            .setContentText("Check your connection")
            .setConfirmClickListener{
                it.dismiss()
            }
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    fun showDialogSuccess(
        title : String,
        messageSuccess : String){
        ProgressLoading.dismiss()
        KAlertDialog(context, KAlertDialog.SUCCESS_TYPE)
            .setTitleText(title)
            .setContentText(messageSuccess)
            .show()
    }

    fun showDiaLogFail(
        title : String,
        messageFail : String){
        ProgressLoading.dismiss()
        KAlertDialog(context, KAlertDialog.ERROR_TYPE)
            .setTitleText(title)
            .setContentText(messageFail)
            .show()
    }
}

interface ConnectionListener {
    fun onButtonClick()
}