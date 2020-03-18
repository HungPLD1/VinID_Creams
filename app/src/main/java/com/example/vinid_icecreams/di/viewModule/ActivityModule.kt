package com.example.vinid_icecreams.di.viewModule

import androidx.appcompat.app.AppCompatActivity
import com.example.vinid_icecreams.ui.activity.home.HomeActivity
import dagger.Binds
import dagger.Module

@Module
interface ActivityModule {

    @Binds
    fun bindMainActivity(activity: HomeActivity): AppCompatActivity
}