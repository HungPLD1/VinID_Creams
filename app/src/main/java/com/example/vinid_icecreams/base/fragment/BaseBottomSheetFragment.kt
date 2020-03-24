package com.example.vinid_icecreams.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.vinid_icecreams.base.BaseView
import com.example.vinid_icecreams.base.viewmodel.BaseViewModel
import com.example.vinid_icecreams.utils.ProgressLoading
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetFragment<T : BaseViewModel> : BottomSheetDialogFragment()
    , BaseView {
    abstract fun providerViewModel(): T

    override fun providerContext(): Context?  = context

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
        setupViewModel()
    }

    override fun setupViewModel() {
        val viewModel = providerViewModel()
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it) {
                ProgressLoading.show(requireContext())
            } else {
                ProgressLoading.dismiss()
            }
        })
    }

    companion object{
        const val ERROR  = "ERROR !!!"
    }
}