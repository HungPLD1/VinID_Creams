package com.example.vinid_icecreams.ui.fragment.home.details

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vinid_icecreams.base.viewmodel.BaseViewModel
import com.example.vinid_icecreams.extension.applySchedulersSingle
import com.example.vinid_icecreams.model.IceCream
import com.example.vinid_icecreams.repository.Repository
import com.example.vinid_icecreams.utils.Const.CODE_200
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    private val _iceCream = MutableLiveData<IceCream>()
    val iceCream: LiveData<IceCream> get() = _iceCream


    @SuppressLint("CheckResult")
    fun getDetailsIceCream(iceCreamID: Int) {
        repository.callRequestDetailsIceCream(iceCreamID)
            ?.compose(applySchedulersSingle(isLoading))
            ?.subscribe({ result ->
                when (result.meta?.code) {
                    CODE_200 -> {
                        _iceCream.value = result.data
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