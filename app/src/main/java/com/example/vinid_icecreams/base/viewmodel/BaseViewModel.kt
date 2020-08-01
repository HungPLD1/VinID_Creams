package com.example.vinid_icecreams.base.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vinid_icecreams.base.view.LoadingState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    val messageSuccess = MutableLiveData<String>()
    val messageFailed = MutableLiveData<String>()
    val isConnection = MutableLiveData<Boolean>()
    val loading = MutableLiveData<LoadingState>()

    private var compositeDisposable: CompositeDisposable? = null

    fun addDisposable(disposable: Disposable) {
        if (compositeDisposable == null || compositeDisposable?.isDisposed ==  true) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable?.add(disposable)
    }

    fun dispose() {
        compositeDisposable?.dispose()
    }

    override fun onCleared() {
        compositeDisposable?.clear()
        super.onCleared()
    }
}