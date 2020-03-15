package com.example.vinid_icecreams.di.repoModule

import com.example.vinid_icecreams.repository.Repository
import com.example.vinid_icecreams.repository.local.ILocalDataSource
import com.example.vinid_icecreams.repository.local.LocalDataSource
import com.example.vinid_icecreams.repository.remote.APIService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [
        ServiceModule::class,
        RealmModule::class
    ]
)
class RepoModule {

    @Singleton
    @Provides
    fun providerRepository(
        apiService: APIService,
        iLocalDataSource: ILocalDataSource
    ): Repository {
        return Repository(apiService, iLocalDataSource)
    }

    @Singleton
    @Provides
    fun providerLocalDataSource(localDataSource: LocalDataSource): ILocalDataSource {
        return localDataSource
    }

}