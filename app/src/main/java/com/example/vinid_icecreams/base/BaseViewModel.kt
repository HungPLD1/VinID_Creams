package com.example.vinid_icecreams.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    val messageSuccess = MutableLiveData<String>()
    val messageFail = MutableLiveData<String>()
    val isConnection = MutableLiveData<Boolean>()

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}