package com.example.vinid_icecreams.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vinid_icecreams.model.Store

class ViewModelIceCream : ViewModel() {
    private var mListStore = MutableLiveData<ArrayList<Store>>()

    fun getListStore () : LiveData<ArrayList<Store>> {
        return mListStore
    }
}