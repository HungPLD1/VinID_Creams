package com.example.vinid_icecreams.base.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    protected val messageSuccess = MutableLiveData<String>()
    protected val messageFailed = MutableLiveData<String>()
    protected val isConnection = MutableLiveData<Boolean>()
    protected val isLoading = MutableLiveData<Boolean>()

    protected var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}