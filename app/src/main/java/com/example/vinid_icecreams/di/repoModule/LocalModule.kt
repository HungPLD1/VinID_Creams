package com.example.vinid_icecreams.di.repoModule

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.vinid_icecreams.repository.local.room.IcreamDatabase
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
    fun providerRoom(
        context: Context
    ): IcreamDatabase {
        return Room.databaseBuilder(context, IcreamDatabase::class.java, "IceCreams")
                    .fallbackToDestructiveMigration()
                    .build()
    }

    @Singleton
    @Provides
    fun providerSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("icream.SharedPreferences", Context.MODE_PRIVATE)
    }
}