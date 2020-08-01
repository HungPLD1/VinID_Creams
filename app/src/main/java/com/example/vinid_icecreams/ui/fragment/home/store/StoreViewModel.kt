package com.example.vinid_icecreams.ui.fragment.home.store

import android.annotation.SuppressLint
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vinid_icecreams.base.viewmodel.BaseViewModel
import com.example.vinid_icecreams.extension.add
import com.example.vinid_icecreams.extension.applySchedulersSingle
import com.example.vinid_icecreams.model.Store
import com.example.vinid_icecreams.repository.Repository
import com.example.vinid_icecreams.repository.remote.MyResponse
import com.example.vinid_icecreams.utils.Const.CODE_200
import java.net.ConnectException
import javax.inject.Inject
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class StoreViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    private val _listStore = MutableLiveData<ArrayList<Store>>()
    val listStore: LiveData<ArrayList<Store>> get() = _listStore

    private val _location = MutableLiveData<Location>()
    val location: LiveData<Location> get() = _location

    @SuppressLint("CheckResult")
    fun getListStore() {
        repository.callRequestListStore()
            ?.compose(applySchedulersSingle(loading))
            ?.map { response -> mapSortStore(response) }
            ?.subscribe({ result ->
                when (result.meta?.code) {
                    CODE_200 -> {
                        _listStore.value = result?.data
                    }
                    else -> {
                        messageFailed.value = result?.meta?.message
                    }
                }
            }) { error ->
                when (error) {
                    is ConnectException -> {
                        isConnection.value = false
                    }
                    else -> {
                        messageFailed.value = error.toString()
                    }
                }
            }?.add(this)
    }

    private fun mapSortStore(response: MyResponse<ArrayList<Store>>): MyResponse<ArrayList<Store>> {
        val currentLocation = repository.getLocation()
        if (currentLocation != null) {
            response.data?.forEach { store ->
                val range = calculationByDistance(
                    currentLocation.latitude
                    , currentLocation.longitude
                    , store.latitude
                    , store.longitude
                )
                store.range = range
                response.data = response.data?.let { sortList(it) }
            }
        }
        return response
    }

    /*fun get rage of two point*/
    private fun calculationByDistance(
        startLatitude: Double,
        startLongitude: Double,
        endLatitude: Double,
        endLongitude: Double
    ): Double {
        val radius = 6371 // radius of earth in Km
        val lat1: Double = startLatitude
        val lat2: Double = endLatitude
        val lon1: Double = startLongitude
        val lon2: Double = endLongitude
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = (sin(dLat / 2) * sin(dLat / 2)
                + (cos(Math.toRadians(lat1))
                * cos(Math.toRadians(lat2)) * sin(dLon / 2)
                * sin(dLon / 2)))
        val c = 2 * asin(sqrt(a))
        return radius * c
    }

    private fun sortList(listData: ArrayList<Store>): ArrayList<Store> {
        return ArrayList(listData.sortedWith(compareBy { it.range }))
    }

    fun getLocation() {
        _location.value = repository.getLocation()
    }
}