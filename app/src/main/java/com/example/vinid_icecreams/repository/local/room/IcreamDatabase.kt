package com.example.vinid_icecreams.repository.local.room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [IceCreamDao::class], version = 1, exportSchema = false)
abstract class IcreamDatabase : RoomDatabase() {
    abstract fun daoAccess(): IceCreamDao?
}