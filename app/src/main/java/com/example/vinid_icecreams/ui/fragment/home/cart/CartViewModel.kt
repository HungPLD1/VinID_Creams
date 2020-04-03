package com.example.vinid_icecreams.ui.fragment.home.cart

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vinid_icecreams.base.viewmodel.BaseViewModel
import com.example.vinid_icecreams.model.Order
import com.example.vinid_icecreams.model.Store
import com.example.vinid_icecreams.repository.Repository
import java.text.FieldPosition
import javax.inject.Inject

class CartViewModel @Inject constructor(
    private val repository : Repository
) : BaseViewModel(){

    private val _listOrder = MutableLiveData<ArrayList<Order>>()
    val listOrder: LiveData<ArrayList<Order>> get() = _listOrder

    fun getListOrder(){
        _listOrder.value = repository.getListOrder()
    }

    fun removeOrder(position: Int){
        repository.removeOrder(position)
    }

    fun increaseOrder(position: Int){
        repository.increaseOrder(position)
    }

    fun decreaseOrder(position: Int){
        repository.decreaseOrder(position)
    }

    fun getStoreSelection() : Store? = repository.getSoreSelection()

    fun getCurrentLocation(): Location? = repository.getLocation()

    fun setTotalPayment (totalPayment : Int){
        repository.setTotalPrice(totalPayment)
    }
}
