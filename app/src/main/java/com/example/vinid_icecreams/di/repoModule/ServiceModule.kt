package com.example.vinid_icecreams.di.repoModule

import android.content.Context
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.repository.remote.APIService
import com.example.vinid_icecreams.utils.CommonUtils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ServiceModule {
       @Provides
       @Singleton
       fun providerGsonBuilder(): Gson {
           return GsonBuilder()
               .setLenient()
               .create()
       }


       @Provides
       @Singleton
       fun providerHttpClientBuilder(
           context: Context
       ): OkHttpClient.Builder {
           val httpClient = OkHttpClient.Builder()
           httpClient.readTimeout(10, TimeUnit.SECONDS)
           httpClient.connectTimeout(10, TimeUnit.SECONDS)
           httpClient.writeTimeout(10, TimeUnit.SECONDS)

           httpClient.addInterceptor { chain ->
               val request: Request =
                   chain.request().newBuilder()
                       .addHeader(context.getString(R.string.TOKEN)
                           ,context.getString(R.string.PREFIX)
                                   + CommonUtils.token).build()
               chain.proceed(request)
           }
           return httpClient
       }
}