package com.example.vinid_icecreams.di.viewModule

import androidx.fragment.app.DialogFragment
import com.example.vinid_icecreams.base.fragment.BaseBottomSheetFragment
import com.example.vinid_icecreams.base.fragment.BaseDialogFragment
import com.example.vinid_icecreams.base.fragment.BaseFragment
import com.example.vinid_icecreams.base.viewmodel.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.Binds
import dagger.Module
import dagger.android.support.DaggerFragment

@Module
interface FragmentModule {

    @Binds
    fun bindFragment(fragment: BaseFragment<BaseViewModel>): DaggerFragment

    @Binds
    fun bindBottomSheetFragment(fragment: BaseBottomSheetFragment<BaseViewModel>): BottomSheetDialogFragment

    @Binds
    fun bindDialogFragment(fragment: BaseDialogFragment<BaseViewModel>): DialogFragment
}