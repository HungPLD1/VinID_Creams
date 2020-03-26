package com.example.vinid_icecreams.di.repoModule

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Singleton

@Module
class LocalModule {

    @Singleton
    @Provides
    fun providerRealm(
        context: Context
    ): Realm {
        Realm.init(context)
        val realmConfig = RealmConfiguration.Builder()
            .name("icream.realm")
            .schemaVersion(0)
            .build()
        return Realm.getInstance(realmConfig)
    }

    @Singleton
    @Provides
    fun providerSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("icream.SharedPreferences", Context.MODE_PRIVATE)
    }
}