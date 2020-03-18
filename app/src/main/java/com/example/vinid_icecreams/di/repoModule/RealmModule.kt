package com.example.vinid_icecreams.di.repoModule

import com.example.vinid_icecreams.repository.local.ILocalDataSource
import com.example.vinid_icecreams.repository.local.LocalDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RealmModule {

    @Singleton
    @Provides
    fun providerLocalDataSource(localDataSource: LocalDataSource): ILocalDataSource {
        return localDataSource
    }
}