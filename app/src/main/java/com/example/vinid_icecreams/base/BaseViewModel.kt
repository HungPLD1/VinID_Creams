package com.example.vinid_icecreams.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    val messageSuccess = MutableLiveData<String>()
    val messageFail = MutableLiveData<String>()
    val isConnection = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}