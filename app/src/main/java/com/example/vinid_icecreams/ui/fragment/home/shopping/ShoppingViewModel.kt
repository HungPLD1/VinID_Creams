package com.example.vinid_icecreams.ui.fragment.home.shopping

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vinid_icecreams.base.BaseViewModel
import com.example.vinid_icecreams.model.IceCream
import com.example.vinid_icecreams.repository.Repository
import com.example.vinid_icecreams.viewmodel.ViewModelIceCream
import javax.inject.Inject

class ShoppingViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    private val _listIceCream = MutableLiveData<ArrayList<IceCream>>()
    val listIceCream: LiveData<ArrayList<IceCream>> get() = _listIceCream

    @SuppressLint("CheckResult")
    fun getListIceCream(storeID: Int) {
        repository.callRequestListIceCream(storeID)?.doOnSubscribe { isLoading.value = true }
            ?.doFinally { isLoading.value = false }?.subscribe({ result ->
                run {
                    when (result.meta?.code) {
                        ViewModelIceCream.CODE_200 -> {
                            _listIceCream.value = result.data
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