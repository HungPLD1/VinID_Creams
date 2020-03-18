package com.example.vinid_icecreams.ui.fragment.home.details

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vinid_icecreams.base.viewmodel.BaseViewModel
import com.example.vinid_icecreams.model.IceCream
import com.example.vinid_icecreams.repository.Repository
import com.example.vinid_icecreams.viewmodel.ViewModelIceCream
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    private val _iceCream = MutableLiveData<IceCream>()
    val iceCream: LiveData<IceCream> get() = _iceCream


    @SuppressLint("CheckResult")
    fun getDetailsIceCream(iceCreamID: Int) {
        repository.callRequestDetailsIceCream(iceCreamID)?.doOnSubscribe { isLoading.value = true }
            ?.doAfterTerminate { isLoading.value = false }?.subscribe({ result ->
                run {
                    when (result.meta?.code) {
                        ViewModelIceCream.CODE_200 -> {
                            _iceCream.value = result.data
                        }
                        else -> {
                            messageFail.value = result?.meta?.message
                        }
                    }
                }
            }) { error ->
                messageFail.value = error.toString()
            }
    }
}