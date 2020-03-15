package com.example.vinid_icecreams.di

import com.example.vinid_icecreams.di.serviceModule.IceCreamServiceModule
import com.example.vinid_icecreams.repository.Repository
import com.example.vinid_icecreams.repository.local.ILocalDataSource
import com.example.vinid_icecreams.repository.local.LocalDataSource
import com.example.vinid_icecreams.repository.remote.APIService
import com.example.vinid_icecreams.repository.remote.RetrofitIceCream
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [IceCreamServiceModule::class])
class AppModule {

    @Singleton
    @Provides
    fun providerRepository(
        apiService: APIService,
        iLocalDataSource: ILocalDataSource
    ): Repository {
        return Repository(apiService,iLocalDataSource)
    }

    @Singleton
    @Provides
    fun providerLocalDataSource(localDataSource: LocalDataSource) : ILocalDataSource {
        return localDataSource
    }

}