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

    var mIsRequestLogin = MutableLiveData<Boolean>()
    var mIsRequestRegister = MutableLiveData<Boolean>()
    var mIsPayment = MutableLiveData<Boolean>()
    var mToken = MutableLiveData<String>()
    var mIsRating = MutableLiveData<Boolean>()
    var mIsChargePoint = MutableLiveData<Boolean>()


    var mMessageSuccess = MutableLiveData<String>()
    var mMessageFail = MutableLiveData<String>()

    companion object {
        val TAG = ViewModelIceCream::class.java.name
        const val CODE_200 = 200
        const val VERIFY_FAIL = "Vui lòng kiểm tra lại thông tin"
        const val REGISTER_FAIL = "Đăng ký không thành công"
    }

    @SuppressLint("CheckResult")
    fun handleRegister(phoneNumber: String, password: String, passwordRepeat: String) {
        if (checkPhoneNumber(phoneNumber) && handleComparedPassword(password, passwordRepeat)) {
            repository.callRegisterAccount(phoneNumber, password)?.subscribe({ result ->
                run {
                    Log.d(TAG, result.meta?.code.toString())
                    when (result.meta?.code) {
                        CODE_200 -> {
                            mMessageSuccess.postValue(result?.meta?.message)
                            mIsRequestRegister.postValue(true)
                            /*post token*/
                            mToken.postValue(result.data?.token)
                        }
                        else -> {
                            mMessageFail.postValue(result?.meta?.message)
                            mIsRequestRegister.postValue(false)
                        }
                    }
                }
            }) { error ->
                run {
                    Log.d(TAG, error.toString())
                    mMessageFail.postValue(error.toString())
                    mIsRequestRegister.postValue(false)
                }
            }
        } else {
            mMessageFail.postValue(REGISTER_FAIL)
            mIsRequestRegister.postValue(false)
            return
        }
    }


    @SuppressLint("CheckResult")
    fun handleLogin(phoneNumber: String, password: String) {
        if (checkPhoneNumber(phoneNumber) && checkPassWord(password)) {
            repository.callLoginAccount(phoneNumber, password)?.subscribe({ result ->
                run {
                    when (result.meta?.code) {
                        CODE_200 -> {
                            /*post data to view */
                            mMessageSuccess.postValue(result?.meta?.message)
                            mIsRequestLogin.postValue(true)
                            /*post token*/
                            mToken.postValue(result.data?.token)
                            Log.d(TAG, result.data?.token.toString())
                        }
                        else -> {
                            /*handle login failse*/
                            mMessageFail.postValue(result?.meta?.message)
                            mIsRequestLogin.postValue(false)
                        }
                    }
                }
            }) { error ->
                run {
                    mMessageFail.postValue(error.toString())
                    mIsRequestLogin.postValue(false)
                }
            }
        } else {
            mMessageFail.postValue(VERIFY_FAIL)
            mIsRequestLogin.postValue(false)
            return
        }
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


    private fun checkPhoneNumber(phoneNumber: String): Boolean {
        if (phoneNumber.isEmpty()) {
            return false
        }
        return android.util.Patterns.PHONE.matcher(phoneNumber).matches()
    }

    private fun checkPassWord(password: String): Boolean {
        if (password.isEmpty()) {
            return false
        }

        return password.length >= 8
    }

    private fun handleComparedPassword(password: String, passwordRepeat: String): Boolean {
        if (password.isEmpty() || passwordRepeat.isEmpty()) {
            return false
        }
        if (password.length < 8 || passwordRepeat.length < 8) {
            return false
        }
        return password == passwordRepeat
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}