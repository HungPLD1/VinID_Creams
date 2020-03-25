package com.example.vinid_icecreams.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vinid_icecreams.repository.remote.requestBody.Bill
import com.example.vinid_icecreams.repository.remote.requestBody.PointRequest
import com.example.vinid_icecreams.model.*
import com.example.vinid_icecreams.repository.Repository
import com.example.vinid_icecreams.repository.remote.requestBody.RatingRequest
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ViewModelIceCream @Inject constructor(
    private val repository: Repository
) :ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    var mListEvent = MutableLiveData<ArrayList<Event>>()
    var mListOrderInfor = MutableLiveData<ArrayList<OrderInfor>>()
    var mListItemOrder = MutableLiveData<ArrayList<ItemOrder>>()
    var mUser = MutableLiveData<User>()

    var mIsRequestRegister = MutableLiveData<Boolean>()
    var mIsPayment = MutableLiveData<Boolean>()
    var mIsRating = MutableLiveData<Boolean>()
    var mIsChargePoint = MutableLiveData<Boolean>()


    var mMessageSuccess = MutableLiveData<String>()
    var mMessageFail = MutableLiveData<String>()

    companion object {
        val TAG = ViewModelIceCream::class.java.name
        const val CODE_200 = 200

    }

    @SuppressLint("CheckResult")
    fun getNotification() {
        repository.callRequestNotification()?.subscribe({ result ->
            run {
                when (result.meta?.code) {
                    CODE_200 -> {
                        mListEvent.postValue(result.data)
                    }
                    else -> {
                        mMessageFail.postValue(result?.meta?.message)
                    }
                }
            }
        }) { error ->
            run {

            }
        }
    }

    @SuppressLint("CheckResult")
    fun handlePayment(bill: Bill) {
        repository.callPayIceCream(bill)?.subscribe({ result ->
            run {
                when (result.meta?.code) {
                    CODE_200 -> {
                        mMessageSuccess.postValue(result?.meta?.message)
                        mIsPayment.postValue(true)
                    }
                    else -> {
                        mMessageFail.postValue(result?.meta?.message)
                        mIsPayment.postValue(false)
                    }
                }
            }
        }) { error ->
            run {
                mMessageFail.postValue(error.toString())
                mIsPayment.postValue(false)
            }
        }
    }

    @SuppressLint("CheckResult")
    fun getOrderUser() {
        repository.callRequestOrderUser()?.subscribe({ result ->
            run {
                when (result.meta?.code) {
                    CODE_200 -> {
                        mListOrderInfor.postValue(result?.data)
                    }
                    else -> {
                        mMessageFail.postValue(result?.meta?.message)
                    }
                }
            }
        }) { error ->
            run {

            }
        }
    }

    @SuppressLint("CheckResult")
    fun getUserProfile() {
        repository.callRequestUserProfile()?.subscribe({ result ->
            run {
                when (result.meta?.code) {
                    CODE_200 -> {
                        mUser.postValue(result?.data)
                    }
                    else -> {
                        mMessageFail.postValue(result?.meta?.message)
                    }
                }
            }
        }) { error ->
            run {
                Log.d(TAG, error.toString())
            }
        }
    }

    @SuppressLint("CheckResult")
    fun getListItemInfo(orderID: Int) {
        repository.callRequestDetailsOrder(orderID)?.subscribe({ result ->
            run {
                when (result.meta?.code) {
                    CODE_200 -> {
                        mListItemOrder.postValue(result.data?.mListItems)
                    }
                    else -> {
                        mMessageFail.postValue(result?.meta?.message)
                    }
                }
            }
        }) { error ->
            run {

            }
        }
    }

    @SuppressLint("CheckResult")
    fun setRatingItem(itemID: Int?, ratingStar: Int?, comment: String?) {
        val bodyRating = RatingRequest(itemID, ratingStar, comment)
        repository.callRequestSetRating(bodyRating)?.subscribe({ result ->
            run {
                when (result.meta?.code) {
                    CODE_200 -> {
                        mIsRating.postValue(true)
                    }
                    else -> {
                        mIsRating.postValue(false)
                        mMessageFail.postValue(result?.meta?.message)
                    }
                }
            }
        }) { error ->
            run {
                mIsRating.postValue(false)
            }
        }
    }

    @SuppressLint("CheckResult")
    fun setPointUser(amount: Int) {
        repository.callRequestChargePoint(PointRequest(amount))?.subscribe({ result ->
            run {
                when (result.meta?.code) {
                    CODE_200 -> {
                        mIsChargePoint.postValue(true)
                        mUser.postValue(result.data)
                    }
                    else -> {
                        mIsChargePoint.postValue(false)
                        mMessageFail.postValue(result.meta?.message)
                    }
                }
            }
        }) { error ->
            run {
                mIsChargePoint.postValue(false)
                mMessageFail.postValue(error.toString())
            }
        }
    }
}