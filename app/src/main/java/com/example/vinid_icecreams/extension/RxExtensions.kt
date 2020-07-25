package com.example.vinid_icecreams.extension

import androidx.lifecycle.MutableLiveData
import com.example.vinid_icecreams.base.view.LoadingState
import com.example.vinid_icecreams.base.viewmodel.BaseViewModel
import io.reactivex.CompletableTransformer
import io.reactivex.FlowableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Used when have single observable
 */
fun <T> applyApiSchedulers(): ObservableTransformer<T, T> {
    return ObservableTransformer { upstream ->
        upstream.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}

fun <T> applyApiSchedulers(
    loading: MutableLiveData<LoadingState>,
    cancellable: Boolean = true
): ObservableTransformer<T, T> {
    return ObservableTransformer { upstream ->
        upstream.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loading.postValue(LoadingState(true, cancellable)) }
            .doOnNext { loading.postValue(LoadingState(false)) }
            .doAfterTerminate { loading.postValue(LoadingState(false)) }
    }
}

fun <T> applyApiSchedulersAsFlowable(): FlowableTransformer<T, T> {
    return FlowableTransformer { upstream ->
        upstream.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}

fun applyApiSchedulersCompletable(): CompletableTransformer {
    return CompletableTransformer { upstream ->
        upstream.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}

fun applyApiSchedulersCompletable(
    loading: MutableLiveData<LoadingState>,
    cancellable: Boolean = true
): CompletableTransformer {
    return CompletableTransformer { upstream ->
        upstream.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loading.postValue(LoadingState(true, cancellable)) }
            .doOnComplete { loading.postValue(LoadingState(false)) }
            .doAfterTerminate { loading.postValue(LoadingState(false)) }
    }
}

fun <T> applySchedulersSingle(): SingleTransformer<T, T> {
    return SingleTransformer { upstream ->
        upstream.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}

fun <T> applySchedulersSingle(
    loading: MutableLiveData<LoadingState>,
    cancellable: Boolean = true
): SingleTransformer<T, T> = SingleTransformer { upstream ->
    upstream.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { loading.postValue(LoadingState(true, cancellable)) }
        .doOnSuccess { loading.postValue(LoadingState(false)) }
        .doAfterTerminate { loading.postValue(LoadingState(false)) }
}

/**
 * Used when have multiple observables
 */
fun <T> applySchedulers(
    loading: MutableLiveData<LoadingState>,
    cancellable: Boolean = true
): ObservableTransformer<T, T> {
    return ObservableTransformer { upstream ->
        upstream.doOnSubscribe { loading.postValue(LoadingState(true, cancellable)) }
            .doOnNext { loading.postValue(LoadingState(false)) }
            .doAfterTerminate { loading.postValue(LoadingState(false)) }
    }
}

fun Disposable.add(baseViewModel: BaseViewModel) {
    baseViewModel.addDisposable(this)
}