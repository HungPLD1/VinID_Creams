package com.example.vinid_icecreams.ui.fragment.home.cart

import android.location.Location
import com.example.vinid_icecreams.base.viewmodel.BaseViewModel
import com.example.vinid_icecreams.model.Order
import com.example.vinid_icecreams.model.Store
import com.example.vinid_icecreams.repository.Repository
import javax.inject.Inject

class CartViewModel @Inject constructor(
    private val repository : Repository
) : BaseViewModel(){

    fun getListOrder(): ArrayList<Order> = repository.getListOrder()

    fun getStoreSelection() : Store? = repository.getSoreSelection()

    fun getCurrentLocation(): Location? = repository.getLocation()

}
