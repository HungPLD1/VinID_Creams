package com.example.vinid_icecreams.di

import com.example.vinid_icecreams.repository.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun providerRepository():Repository{
        return Repository.mInstance
    }




}