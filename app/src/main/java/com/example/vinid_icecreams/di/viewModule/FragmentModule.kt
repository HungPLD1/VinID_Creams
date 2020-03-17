package com.example.vinid_icecreams.di.viewModule

import com.example.vinid_icecreams.base.BaseFragment
import com.example.vinid_icecreams.base.BaseViewModel
import dagger.Binds
import dagger.Module
import dagger.android.support.DaggerFragment

@Module
interface FragmentModule {

    @Binds
    fun bindFragment(fragment: BaseFragment<BaseViewModel>): DaggerFragment
}