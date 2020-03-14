package com.example.vinid_icecreams.di.viewModule

import androidx.lifecycle.ViewModel
import com.example.vinid_icecreams.base.BaseFragment
import com.example.vinid_icecreams.base.BaseViewModel
import com.example.vinid_icecreams.di.viewModelModule.ViewModelKey
import com.example.vinid_icecreams.ui.fragment.home.details.DetailsViewModel
import com.example.vinid_icecreams.ui.fragment.home.shopping.ShoppingViewModel
import com.example.vinid_icecreams.ui.fragment.home.store.StoreViewModel
import dagger.Binds
import dagger.Module
import dagger.android.support.DaggerFragment
import dagger.multibindings.IntoMap

@Module
interface FragmentModule {

    @Binds
    fun bindFragment(fragment: BaseFragment<BaseViewModel>): DaggerFragment

}