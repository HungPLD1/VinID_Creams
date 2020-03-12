package com.example.vinid_icecreams.di.viewModule

import com.example.vinid_icecreams.ui.activity.HomeActivity
import com.example.vinid_icecreams.ui.fragment.home.store.StoreFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ViewBuilder {
    @ContributesAndroidInjector(modules = [ActivityModule::class])
    fun contributeMainActivity(): HomeActivity

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    fun contributeStoreFragment(): StoreFragment
}