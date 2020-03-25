package com.example.vinid_icecreams.base.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    val messageSuccess = MutableLiveData<String>()
    val messageFailed = MutableLiveData<String>()
    val isConnection = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()

    protected var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}