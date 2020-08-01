package com.example.vinid_icecreams.ui.fragment.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vinid_icecreams.base.viewmodel.BaseViewModel
import com.example.vinid_icecreams.extension.add
import com.example.vinid_icecreams.extension.applySchedulersSingle
import com.example.vinid_icecreams.model.Event
import com.example.vinid_icecreams.repository.Repository
import com.example.vinid_icecreams.utils.Const.CODE_200
import javax.inject.Inject

class EventViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    private val _listEvent = MutableLiveData<ArrayList<Event>>()
    val listEvent: LiveData<ArrayList<Event>> get() = _listEvent

    fun getNotification() {
        repository.callRequestNotification()
            ?.compose(applySchedulersSingle(loading))
            ?.subscribe({ result ->
            when (result.meta?.code) {
                CODE_200 -> {
                    _listEvent.value = result.data
                }
                else -> {
                    messageFailed.value = result?.meta?.message
                }
            }
        }) { error ->
            messageFailed.value = error.toString()
        }?.add(this)
    }
}