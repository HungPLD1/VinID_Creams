package com.example.vinid_icecreams.di.repoModule

import android.content.Context
import android.content.SharedPreferences
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.utils.Const
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [LocalModule::class])
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
           context: Context,
           sharedPref : SharedPreferences
       ): OkHttpClient.Builder {
           val httpClient = OkHttpClient.Builder()
           httpClient.readTimeout(10, TimeUnit.SECONDS)
           httpClient.connectTimeout(10, TimeUnit.SECONDS)
           httpClient.writeTimeout(10, TimeUnit.SECONDS)

           httpClient.addInterceptor { chain ->
               val request: Request =
                   chain.request().newBuilder()
                       .addHeader(context.getString(R.string.TOKEN)
                           ,context.getString(R.string.PREFIX)+" "
                                   + sharedPref.getString(Const.TOKEN,null)).build()
               chain.proceed(request)
           }
           return httpClient
       }
}