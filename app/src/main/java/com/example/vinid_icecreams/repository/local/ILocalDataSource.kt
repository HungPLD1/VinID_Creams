package com.example.vinid_icecreams.repository.local

import com.example.vinid_icecreams.model.Order

interface ILocalDataSource {
    fun saveToken(token : String?)
    fun getToken() : String?
    fun saveListOrder(dataOrder : ArrayList<Order>)
    fun getListOrder() : ArrayList<Order>
}