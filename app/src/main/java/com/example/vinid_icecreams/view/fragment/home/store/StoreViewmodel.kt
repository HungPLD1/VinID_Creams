package com.example.vinid_icecreams.view.fragment.home.store

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vinid_icecreams.base.BaseViewModel
import com.example.vinid_icecreams.model.Store
import com.example.vinid_icecreams.repository.Repository
import com.example.vinid_icecreams.viewmodel.ViewModelIceCream
import javax.inject.Inject

class StoreViewmodel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    private val _listStore = MutableLiveData<ArrayList<Store>>()
    val listStore: LiveData<ArrayList<Store>> get() = _listStore

    @SuppressLint("CheckResult")
    fun getListStore() {
        repository.callRequestListStore()?.subscribe({ result ->
                when (result.meta?.code) {
                    ViewModelIceCream.CODE_200 -> {
                        messageSuccess.value = result?.meta?.message
                        _listStore.value = result.data
                    }
                    else -> {
                        messageFail.postValue(result?.meta?.message)
                    }
                }
        }) { error ->

        }
    }
}