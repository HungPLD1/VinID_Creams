package com.example.vinid_icecreams.di.viewModule

import com.example.vinid_icecreams.view.activity.HomeActivity
import com.example.vinid_icecreams.view.fragment.home.store.StoreFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import net.vinid.demodaggerandroid.di.activitymodule.ActivityModule

@Module
interface ViewBuilder {
    @ContributesAndroidInjector(modules = [ActivityModule::class])
    fun contributeMainActivity(): HomeActivity

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    fun contributeStoreFragment(): StoreFragment
}