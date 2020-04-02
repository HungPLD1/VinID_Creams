package com.example.vinid_icecreams.ui.activity.home

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

    private var listOrder = repository.getListOrder()

    private val _totalPayment = MutableLiveData<Int>()
    val totalPayment: LiveData<Int> get() = _totalPayment

    fun addOrder(order: Order) {
        if (listOrder.size > 0) {
            val i = listOrder.size - 1
            if (order.iceCream.id == listOrder[i].iceCream.id) {
                repository.increaseOrder(i)
            } else {
                repository.addOrder(order)
            }
        } else {
            repository.addOrder(order)
        }
    }

    fun getListOrder(): ArrayList<Order> = repository.getListOrder()

    fun setOrderPayment(total: Int){
        _totalPayment.value = total
    }

    fun setStoreSelection(store : Store){
        repository.saveStoreSelection(store)
    }

    fun getStoreSelection() : Store?{
        return repository.getSoreSelection()
    }

    fun resetOrder(){
        repository.saveListOrder(ArrayList())
    }
}