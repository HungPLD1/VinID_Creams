package com.example.vinid_icecreams.di.viewModelModule

import androidx.lifecycle.ViewModelProvider
import com.example.vinid_icecreams.di.viewModelModule.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}
