package com.example.vinid_icecreams.ui.fragment.home.requestLocation

import android.location.Location
import com.example.vinid_icecreams.base.viewmodel.BaseViewModel
import com.example.vinid_icecreams.repository.Repository
import javax.inject.Inject

class RequestLocationViewModel @Inject constructor(
    private val repository: Repository
): BaseViewModel() {

    fun saveLocation (location : Location){
        repository.saveLocation(location)
    }

    fun getLocation () : Location? {
        return repository.getLocation()
    }
}