package com.example.vinid_icecreams.base.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.vinid_icecreams.base.BaseView
import com.example.vinid_icecreams.base.viewmodel.BaseViewModel
import com.example.vinid_icecreams.utils.ProgressLoading

abstract class BaseActivity<T : BaseViewModel> : AppCompatActivity(),BaseView {
    abstract fun providerViewModel(): T

    override fun providerContext(): Context?  = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpUI()
        setupViewModel()
    }

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