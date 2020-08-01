package com.example.vinid_icecreams.ui.fragment.home.pay

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vinid_icecreams.base.viewmodel.BaseViewModel
import com.example.vinid_icecreams.extension.add
import com.example.vinid_icecreams.extension.applySchedulersSingle
import com.example.vinid_icecreams.model.Store
import com.example.vinid_icecreams.repository.Repository
import com.example.vinid_icecreams.repository.remote.requestBody.BillRequest
import com.example.vinid_icecreams.utils.Const.CODE_200
import javax.inject.Inject

class PayViewModel @Inject constructor(
    private val repository: Repository
)  : BaseViewModel() {

    private val _isPayment = MutableLiveData<Boolean>()
    val isPayment: LiveData<Boolean> get() = _isPayment

    @SuppressLint("CheckResult")
    fun handlePayment(billRequest: BillRequest) {
        repository.callPayIceCream(billRequest)
            ?.compose(applySchedulersSingle(loading))
            ?.subscribe({ result ->
                when (result.meta?.code) {
                    CODE_200 -> {
                        messageSuccess.value = result?.meta?.message
                        _isPayment.value = true
                    }
                    else -> {
                        messageFailed.value = result?.meta?.message
                        _isPayment.value = false
                    }
                }
        }) { error ->
            messageFailed.value = error.toString()
            _isPayment.value = false
        }?.add(this)
    }

    fun getTotalPayment() : Int? = repository.getTotalPrice()

    fun getStoreSelected() : Store? = repository.getSoreSelection()

    fun clearOrder(){
        repository.setEmptyOrder()
    }
}