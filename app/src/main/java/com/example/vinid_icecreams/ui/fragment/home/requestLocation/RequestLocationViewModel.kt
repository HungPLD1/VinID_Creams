package com.example.vinid_icecreams.ui.fragment.home.requestLocation

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vinid_icecreams.base.viewmodel.BaseViewModel
import com.example.vinid_icecreams.repository.Repository
import javax.inject.Inject

class RequestLocationViewModel @Inject constructor(
    private val repository: Repository
): BaseViewModel() {


    private val _isSaveLocationSuccess = MutableLiveData<Boolean>()
    val isSaveLocationSuccess: LiveData<Boolean> get() = _isSaveLocationSuccess

    fun saveLocation (location : Location){
        repository.saveLocation(location)
        _isSaveLocationSuccess.value = true
    }
}