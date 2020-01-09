package com.example.vinid_icecreams

import android.app.Application
import io.reactivex.plugins.RxJavaPlugins

class IceCreamApplication : Application() {

    companion object{
        val instance = this
    }


    override fun onCreate() {
        super.onCreate()
        RxJavaPlugins.setErrorHandler { throwable ->
            run {

            }
        }
    }
}