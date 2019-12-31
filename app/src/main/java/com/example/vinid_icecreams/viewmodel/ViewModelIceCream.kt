package com.example.vinid_icecreams.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vinid_icecreams.mock.MockData
import com.example.vinid_icecreams.model.Store
import com.example.vinid_icecreams.repository.Repository

class ViewModelIceCream : ViewModel() {
    private var mListStore  = MutableLiveData<ArrayList<Store>>()

    fun getListStore () : LiveData<ArrayList<Store>> {
        Log.d("hungpld1",Repository.mInstance.getListStore().size.toString())
        mListStore.value = MockData.getListStore()
        return mListStore
    }
}