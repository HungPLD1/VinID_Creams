package com.example.vinid_icecreams.di.viewModule

import com.example.vinid_icecreams.base.activity.BaseActivity
import com.example.vinid_icecreams.base.viewmodel.BaseViewModel
import dagger.Binds
import dagger.Module
import dagger.android.support.DaggerAppCompatActivity

@Module
interface ActivityModule {

    @Binds
    fun bindMainActivity(homeActivity: BaseActivity<BaseViewModel>): DaggerAppCompatActivity
}