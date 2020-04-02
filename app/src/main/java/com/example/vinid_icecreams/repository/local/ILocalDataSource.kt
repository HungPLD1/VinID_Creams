package com.example.vinid_icecreams.repository.local

import android.location.Location
import com.example.vinid_icecreams.model.Order
import com.example.vinid_icecreams.model.Store

interface ILocalDataSource {
    fun saveToken(token : String?)
    fun getToken() : String?
    fun saveListOrder(dataOrder : ArrayList<Order>)
    fun getListOrder() : ArrayList<Order>
    fun saveOrder(order: Order)
    fun removeOrder(position: Int)
    fun increaseOrder(position: Int)
    fun saveTotalPrice(total : Int)
    fun getTotalPrice(): Int?
    fun saveLocation(location: Location?)
    fun getLocation() : Location?
    fun saveStoreSelection(store: Store)
    fun getStoreSelection(): Store?
}