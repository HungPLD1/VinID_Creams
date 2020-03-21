package com.example.vinid_icecreams.ui.activity.home

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vinid_icecreams.base.viewmodel.BaseViewModel
import com.example.vinid_icecreams.model.Order
import com.example.vinid_icecreams.model.Store
import com.example.vinid_icecreams.repository.Repository
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    private val _listOrder = MutableLiveData<ArrayList<Order>>()
    val listOrder: LiveData<ArrayList<Order>> get() = _listOrder

    private val _totalPayment = MutableLiveData<Int>()
    val totalPayment: LiveData<Int> get() = _totalPayment

    private val _storeSelection = MutableLiveData<Store>()
    val storeSelection: LiveData<Store> get() = _storeSelection


    private var currentLocation : Location? = null

    fun addOrder(order: Order) {
        if (_listOrder.value?.size!! > 0) {
            val i = _listOrder.value?.size!! - 1
            if (order.mIceCream.id == _listOrder.value!![i].mIceCream.id) {
                listOrder.value!![i].mAmount += 1
            } else {
                listOrder.value?.add(order)
            }
        } else {
            listOrder.value?.add(order)
        }
    }

    fun setOrderPayment(total: Int){
        _totalPayment.value = total
    }

    fun setStoreSelection(store : Store){
        _storeSelection.value = store
    }

    fun setLocation(location : Location){
        currentLocation = location
    }

    fun getLocation() : Location? = currentLocation
}