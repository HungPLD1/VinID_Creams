package com.example.vinid_icecreams.repository.local.room

import androidx.room.*
import com.example.vinid_icecreams.model.IceCream

@Dao
interface IceCreamDao{

    @Insert
    fun insertOnlySingleIceCream (iceCream : IceCream)

    @Insert
    fun insertAllIceCream (iceCreams:  ArrayList<IceCream>)

    @Query("SELECT * FROM IceCream WHERE id = :idIceCream")
    fun getIceCreamByID (idIceCream : Int)

    @Query("SELECT * FROM IceCream")
    fun getAllIceCream()
}