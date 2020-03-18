package com.example.vinid_icecreams.di.viewModule

import com.example.vinid_icecreams.base.fragment.BaseFragment
import com.example.vinid_icecreams.base.viewmodel.BaseViewModel
import dagger.Binds
import dagger.Module
import dagger.android.support.DaggerFragment

@Module
interface FragmentModule {

    @Binds
    fun bindFragment(fragment: BaseFragment<BaseViewModel>): DaggerFragment
}