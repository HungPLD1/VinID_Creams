package com.example.vinid_icecreams.ui.fragment.event

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vinid_icecreams.base.viewmodel.BaseViewModel
import com.example.vinid_icecreams.model.Event
import com.example.vinid_icecreams.repository.Repository
import com.example.vinid_icecreams.viewmodel.ViewModelIceCream
import javax.inject.Inject

class EventViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    private val _listEvent = MutableLiveData<ArrayList<Event>>()
    val listEvent: LiveData<ArrayList<Event>> get() = _listEvent

    @SuppressLint("CheckResult")
    fun getNotification() {
        repository.callRequestNotification()
            ?.doOnSubscribe { isLoading.value = true }
            ?.doFinally { isLoading.value = false }
            ?.subscribe({ result ->
            when (result.meta?.code) {
                ViewModelIceCream.CODE_200 -> {
                    _listEvent.value = result.data
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