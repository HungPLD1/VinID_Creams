package com.example.vinid_icecreams.ui.activity.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vinid_icecreams.base.viewmodel.BaseViewModel
import com.example.vinid_icecreams.model.Order
import com.example.vinid_icecreams.repository.Repository
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    private val _listOrder = MutableLiveData<ArrayList<Order>>()
    val listOrder: LiveData<ArrayList<Order>> get() = _listOrder

    fun setOrderToList(order: Order) {
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

}