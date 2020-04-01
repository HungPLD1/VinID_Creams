package com.example.vinid_icecreams.repository.local

import android.location.Location
import com.example.vinid_icecreams.model.Order

interface ILocalDataSource {
    fun saveToken(token : String?)
    fun getToken() : String?
    fun saveListOrder(dataOrder : ArrayList<Order>)
    fun getListOrder() : ArrayList<Order>
    fun saveLocation(location: Location?)
    fun getLocation() : Location?
}