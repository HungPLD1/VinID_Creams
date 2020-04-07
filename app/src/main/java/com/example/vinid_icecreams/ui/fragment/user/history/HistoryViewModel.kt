package com.example.vinid_icecreams.ui.fragment.user.history

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vinid_icecreams.base.viewmodel.BaseViewModel
import com.example.vinid_icecreams.model.OrderInfor
import com.example.vinid_icecreams.repository.Repository
import com.example.vinid_icecreams.utils.Const.CODE_200
import javax.inject.Inject

class HistoryViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel(){

    private val _listOrderInfor = MutableLiveData<ArrayList<OrderInfor>>()
    val listOrderInfor: LiveData<ArrayList<OrderInfor>> get() = _listOrderInfor

    @SuppressLint("CheckResult")
    fun getOrderUser() {
        repository.callRequestOrderUser()
            ?.doOnSubscribe { isLoading.value = true }
            ?.doFinally { isLoading.value = false }
            ?.subscribe({ result ->
                when (result.meta?.code) {
                    CODE_200 -> {
                        _listOrderInfor.value = result?.data
                    }
                    else -> {
                        messageFailed.value = result?.meta?.message
                    }
                }
        }) { error ->
            messageFailed.value = error.toString()
        }
    }
}