package com.example.vinid_icecreams.base

import android.content.Context
import androidx.lifecycle.Observer
import com.example.vinid_icecreams.utils.ProgressLoading
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDiaFragment<T : BaseViewModel> : BottomSheetDialogFragment()
    , BaseView {
    abstract fun providerViewModel(): T

    override fun providerContext(): Context? = context

    fun observeLoading() {
        providerViewModel().isLoading.observe(viewLifecycleOwner, Observer {
            if (it) {
                ProgressLoading.show(requireContext())
            } else {
                ProgressLoading.dismiss()
            }
        })
    }
}