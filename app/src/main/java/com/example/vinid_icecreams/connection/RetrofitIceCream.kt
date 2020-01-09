package com.example.vinid_icecreams.connection

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitIceCream {
    private const val TOKEN = "token"
    private const val BASE_URL = "http://35.198.221.214:8080"
    private var retrofit: Retrofit? = null



    fun createRetrofit(): APIService? {
        val httpClient = OkHttpClient.Builder()
        httpClient.readTimeout(10, TimeUnit.SECONDS)
        httpClient.connectTimeout(10, TimeUnit.SECONDS)
        httpClient.writeTimeout(10,TimeUnit.SECONDS)

        httpClient.addInterceptor { chain ->
            val request: Request =
                chain.request().newBuilder().build()
            chain.proceed(request)
        }

        val gSon = GsonBuilder()
            .setLenient()
            .create()

        if (retrofit == null) {
            retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(httpClient.build())
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create()
                ).addConverterFactory(GsonConverterFactory.create(gSon)).build()
        }
        return retrofit?.create(APIService::class.java)
    }

}