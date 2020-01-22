package com.example.vinid_icecreams.di

import androidx.fragment.app.Fragment
import com.example.vinid_icecreams.MyApplication
import com.example.vinid_icecreams.viewmodel.ViewModelIceCream
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(application: MyApplication)
    fun inject(modelIceCream: ViewModelIceCream)
    fun inject(fragment: Fragment)
}