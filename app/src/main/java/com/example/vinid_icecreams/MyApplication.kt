package com.example.vinid_icecreams


import android.app.Application
import com.example.vinid_icecreams.di.AppComponent
import com.example.vinid_icecreams.di.AppModule
import com.example.vinid_icecreams.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class MyApplication : Application() {

    companion object{
       lateinit var component: AppComponent
    }
    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder().appModule(AppModule()).build()
    }


}