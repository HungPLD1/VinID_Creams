package com.example.vinid_icecreams.di

import android.content.Context
import com.example.vinid_icecreams.MyApplication
import com.example.vinid_icecreams.di.repoModule.RepoModule
import com.example.vinid_icecreams.di.repoModule.ServiceModule
import com.example.vinid_icecreams.di.viewModelModule.ViewModelModule
import com.example.vinid_icecreams.di.viewModule.ViewBuilder
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [RepoModule::class,
        ViewModelModule::class,
        ViewBuilder::class,
        AndroidSupportInjectionModule::class]
)
interface AppComponent : AndroidInjector<MyApplication> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }
}