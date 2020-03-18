package com.example.vinid_icecreams.di.viewModelModule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vinid_icecreams.ui.activity.home.HomeViewModel
import com.example.vinid_icecreams.ui.fragment.home.details.DetailsViewModel
import com.example.vinid_icecreams.ui.fragment.home.shopping.ShoppingViewModel
import com.example.vinid_icecreams.ui.fragment.home.store.StoreViewModel
import com.example.vinid_icecreams.viewmodel.ViewModelIceCream
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ViewModelIceCream::class)
    fun bindDataViewModel(
        dataViewModel: ViewModelIceCream
    ): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(StoreViewModel::class)
    fun bindDataStoreViewModel(
        dataViewModel: StoreViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShoppingViewModel::class)
    fun bindDataShoppingViewModel(
        dataViewModel: ShoppingViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    fun bindDataDetailsViewModel(
        dataViewModel: DetailsViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun binDataHomeViewModel(
        dataViewModel: HomeViewModel
    ): ViewModel
}