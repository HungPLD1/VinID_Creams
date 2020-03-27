package com.example.vinid_icecreams.base.activity

import android.content.Context
import android.view.View
import androidx.lifecycle.Observer
import com.example.vinid_icecreams.base.BaseView
import com.example.vinid_icecreams.base.viewmodel.BaseViewModel
import com.example.vinid_icecreams.utils.ProgressLoading
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity<T : BaseViewModel> : DaggerAppCompatActivity(),BaseView {
    abstract fun providerViewModel(): T

    override fun providerContext(): Context?  = this

    override fun provideRootView(): View?  =  findViewById(android.R.id.content)

    override fun setupViewModel() {
        val viewModel = providerViewModel()
        viewModel.isLoading.observe(this, Observer {
            if (it) {
                ProgressLoading.show(this)
            } else {
                ProgressLoading.dismiss()
            }
        })
    }

}