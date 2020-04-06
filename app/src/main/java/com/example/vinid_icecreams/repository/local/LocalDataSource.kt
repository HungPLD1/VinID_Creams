package com.example.vinid_icecreams.repository.local

import android.content.SharedPreferences
import android.location.Location
import com.example.vinid_icecreams.model.Order
import com.example.vinid_icecreams.model.Store
import com.example.vinid_icecreams.utils.Const
import io.realm.Realm
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val realm: Realm,
    private val sharedPref: SharedPreferences
) : ILocalDataSource {

    override fun saveToken(token: String?) {
        sharedPref.edit().putString(Const.TOKEN, token).apply()
    }

    override fun getToken(): String? {
        return sharedPref.getString(Const.TOKEN, null)
    }

    override fun saveListOrder(dataOrder: ArrayList<Order>) {
        /* realm.beginTransaction()
         val realmList = RealmList<Order>()
         realmList.addAll(listOrder)
         realm.commitTransaction()*/
        listOrder.addAll(dataOrder)
    }

    override fun getListOrder(): ArrayList<Order> {
        /* var listOrder =  ArrayList<Order>()
         realm.where(Order::class.java).findAll().forEach {
             listOrder.add(it)
         }*/
        return listOrder
    }

    override fun saveOrder(order: Order) {
        listOrder.add(order)
    }

    override fun removeOrder(position: Int) {
        listOrder.removeAt(position)
    }

    override fun increaseOrder(position: Int) {
        listOrder[position].amount += 1
    }

    override fun decreaseOrder(position: Int) {
        listOrder[position].amount -= 1
    }

    override fun setEmptyOrder() {
        listOrder.clear()
    }

    override fun saveTotalPrice(total: Int) {
        totalPrice = total
    }

    override fun getTotalPrice(): Int? = totalPrice

    override fun saveLocation(location: Location?) {
        currentLocation = location
    }

    override fun getLocation(): Location? = currentLocation

    override fun saveStoreSelection(store: Store) {
        storeSelection = store
    }

    override fun getStoreSelection(): Store? = storeSelection

    companion object {
        var listOrder = ArrayList<Order>()
        var currentLocation: Location? = null
        var storeSelection: Store? = null
        var totalPrice: Int? = null
    }
}