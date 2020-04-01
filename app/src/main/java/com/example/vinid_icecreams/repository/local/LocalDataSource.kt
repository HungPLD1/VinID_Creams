package com.example.vinid_icecreams.repository.local

import android.content.SharedPreferences
import com.example.vinid_icecreams.model.Order
import com.example.vinid_icecreams.utils.Const
import io.realm.Realm
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val realm: Realm,
    private val sharedPref :SharedPreferences
) : ILocalDataSource {

    override fun saveToken(token: String?) {
        sharedPref.edit().putString(Const.TOKEN,token).apply()
    }

    override fun getToken(): String? {
        return sharedPref.getString(Const.TOKEN,null)
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

    companion object{
        var listOrder = ArrayList<Order>()
    }
}