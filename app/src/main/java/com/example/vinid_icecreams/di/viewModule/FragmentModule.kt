package com.example.vinid_icecreams.di.viewModule

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.vinid_icecreams.di.viewModelModule.ViewModelKey
import com.example.vinid_icecreams.ui.fragment.home.store.StoreFragment
import com.example.vinid_icecreams.ui.fragment.home.store.StoreViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface FragmentModule {

    @Binds
    fun bindFragment(fragment: StoreFragment): Fragment

    @Binds
    @IntoMap
    @ViewModelKey(StoreViewModel::class)
    fun bindDataViewModel(
        dataViewModel: StoreViewModel
    ): ViewModel

}