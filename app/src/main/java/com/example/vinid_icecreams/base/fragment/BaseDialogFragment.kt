package com.example.vinid_icecreams.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.example.vinid_icecreams.base.view.BaseView
import com.example.vinid_icecreams.base.viewmodel.BaseViewModel
import com.example.vinid_icecreams.utils.ProgressLoading
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseDialogFragment<T : BaseViewModel> : DialogFragment()
    , HasAndroidInjector, BaseView {
    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Any>

    abstract fun providerViewModel(): T

    override fun providerContext(): Context?  = context

    override fun provideRootView(): View?  =  view

    protected var messageSuccess : String? = null

    protected var messageFailed :String? = null

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
        setupViewModel()
    }

    override fun setupViewModel() {
        val viewModel = providerViewModel()
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if (it.isLoading) {
                ProgressLoading.show(requireContext())
            } else {
                ProgressLoading.dismiss()
            }
        })

        viewModel.messageSuccess.observe(viewLifecycleOwner, Observer {
            messageSuccess = it
        })

        viewModel.messageFailed.observe(viewLifecycleOwner, Observer {
            messageFailed = it
        })
    }

    override fun androidInjector(): AndroidInjector<Any>? {
        return childFragmentInjector
    }

    companion object{
        const val ERROR  = "ERROR !!!"
    }
}