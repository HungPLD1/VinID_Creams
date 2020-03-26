package com.example.vinid_icecreams.di.repoModule

import android.content.Context
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.repository.Repository
import com.example.vinid_icecreams.repository.local.ILocalDataSource
import com.example.vinid_icecreams.repository.local.LocalDataSource
import com.example.vinid_icecreams.repository.remote.APIService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(
    includes = [
        ServiceModule::class
        , LocalModule::class]
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

    @Provides
    @Singleton
    fun providerRetrofitBuilder(
        gSon: Gson,
        clientBuilder: OkHttpClient.Builder,
        context: Context
    ): APIService {
        val retrofit = Retrofit.Builder().baseUrl(
            context.getString(R.string.BASE_URL)
        ).client(clientBuilder.build())
            .addCallAdapterFactory(
                RxJava2CallAdapterFactory.create()
            ).addConverterFactory(GsonConverterFactory.create(gSon)).build()
        return retrofit.create(APIService::class.java)
    }

    @Singleton
    @Provides
    fun providerLocalDataSource(
        localDataSource: LocalDataSource
    ): ILocalDataSource {
        return localDataSource
    }
}