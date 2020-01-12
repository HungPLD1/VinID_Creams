package com.example.vinid_icecreams.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vinid_icecreams.connection.body.Bill
import com.example.vinid_icecreams.model.*
import com.example.vinid_icecreams.repository.Repository

class ViewModelIceCream : ViewModel() {
    var mListStore = MutableLiveData<ArrayList<Store>>()
    var mListIceCream = MutableLiveData<ArrayList<IceCream>>()
    var mListEvent = MutableLiveData<ArrayList<Event>>()
    var mIceCream = MutableLiveData<IceCream>()
    var mListOrderInfor = MutableLiveData<ArrayList<OrderInfor>>()

    var mIsRequestLogin = MutableLiveData<Boolean>()
    var mIsRequestRegister = MutableLiveData<Boolean>()
    var mIsPayment = MutableLiveData<Boolean>()
    var mToken = MutableLiveData<String>()

    var mMessageSuccess = MutableLiveData<String>()
    var mMessageFailse = MutableLiveData<String>()

    companion object {
        val TAG = ViewModelIceCream::class.java.name
        const val CODE_200 = 200
        const val VERIFY_FAILSE = "Vui lòng kiểm tra lại thông tin"
        const val REGISTER_FAILSE = "Đăng ký không thành công"
    }

    @SuppressLint("CheckResult")
    fun getListStore() {
        Repository.mInstance.callRequestListStore()?.subscribe({ result ->
            run {
                when (result.meta?.code) {
                    CODE_200 -> {
                        mListStore.postValue(result.data)
                        mMessageSuccess.postValue(result?.meta?.message)
                    }
                    else -> {
                        mMessageFailse.postValue(result?.meta?.message)
                    }
                }
            }
        }) { error ->
            run {

            }
        }
    }

    @SuppressLint("CheckResult")
    fun getListIceCream(storeID: Int) {
        Repository.mInstance.callRequestListIceCream(storeID)?.subscribe({ result ->
            run {
                when (result.meta?.code) {
                    CODE_200 -> {
                        mListIceCream.postValue(result.data)
                    }
                    else -> {
                        mMessageFailse.postValue(result?.meta?.message)
                    }
                }
            }
        }) { error ->
            run {

            }
        }
    }

    @SuppressLint("CheckResult")
    fun getDetailsIceCream(iceCreamID: Int) {
        Repository.mInstance.callRequestDetailsIceCream(iceCreamID)?.subscribe({ result ->
            run {
                when (result.meta?.code) {
                    CODE_200 -> {
                        mIceCream.postValue(result.data)
                    }
                    else -> {
                        mMessageFailse.postValue(result?.meta?.message)
                    }
                }
            }
        }) { error ->
            run {

            }
        }
    }

    @SuppressLint("CheckResult")
    fun handleRegister(phoneNumber: String, password: String, passwordRepeat: String) {
        if (checkPhoneNumber(phoneNumber) && handleComparedPassword(password, passwordRepeat)) {
            Repository.mInstance.callRegisterAccount(phoneNumber, password)?.subscribe({ result ->
                run {
                    Log.d(TAG, result.meta?.code.toString())
                    when (result.meta?.code) {
                        CODE_200 -> {
                            mIsRequestRegister.value = true
                            mMessageSuccess.value = result?.meta?.message
                            /*post token*/
                            mToken.value = result.data?.token
                        }
                        else -> {
                            mIsRequestRegister.value = false
                            mMessageFailse.value = result?.meta?.message
                        }
                    }
                }
            }) { error ->
                run {
                    Log.d(TAG, error.toString())
                    mIsRequestRegister.value = false
                    mMessageFailse.value = error.toString()
                }
            }
        } else {
            mIsRequestRegister.value = false
            mMessageFailse.value = REGISTER_FAILSE
            return
        }
    }


    @SuppressLint("CheckResult")
    fun handleLogin(phoneNumber: String, password: String) {
        if (checkPhoneNumber(phoneNumber) && checkPassWord(password)) {
            Repository.mInstance.callLoginAccount(phoneNumber, password)?.subscribe({ result ->
                run {
                    when (result.meta?.code) {
                        CODE_200 -> {
                            /*post data to view */
                            mIsRequestLogin.value = true
                            mMessageSuccess.value = result?.meta?.message
                            /*post token*/
                            mToken.value = result.data?.token
                            Log.d(TAG, result.data?.token.toString())
                        }
                        else -> {
                            /*handle login failse*/
                            mIsRequestLogin.value = false
                            mMessageFailse.value = result?.meta?.message
                        }
                    }
                }
            }) { error ->
                run {
                    mMessageFailse.value = error.toString()
                    mIsRequestLogin.value = false
                }
            }
        } else {
            mIsRequestLogin.value = false
            mMessageFailse.value = VERIFY_FAILSE
            return
        }
    }

    @SuppressLint("CheckResult")
    fun getNotification() {
        Repository.mInstance.callRequestNotification()?.subscribe({ result ->
            run {
                when (result.meta?.code) {
                    CODE_200 -> {
                        mListEvent.postValue(result.data)
                    }
                    else -> {
                        mMessageFailse.postValue(result?.meta?.message)
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
        Repository.mInstance.callPayIceCream(bill)?.subscribe({ result ->
            run {
                when (result.meta?.code) {
                    CODE_200 -> {
                        mIsPayment.value = true
                        mMessageSuccess.value = result?.meta?.message
                    }
                    else -> {
                        mIsPayment.value = false
                        mMessageFailse.value = result?.meta?.message
                    }
                }
            }
        }) { error ->
            run {
                mIsPayment.value = false
                mMessageFailse.value = error.toString()
            }
        }
    }

    @SuppressLint("CheckResult")
    fun getOrderUser() {
        Repository.mInstance.callRequestOrderUser()?.subscribe({ result ->
            run {
                when (result.meta?.code) {
                    CODE_200 -> {
                        mListOrderInfor.value = result.data
                    }
                    else -> {
                        mMessageFailse.postValue(result?.meta?.message)
                    }
                }
            }
        }) { error ->
            run {

            }
        }
    }


    private fun checkPhoneNumber(phoneNumber: String): Boolean {
        if (phoneNumber.isEmpty()) {
            return false
        }
        return phoneNumber.length != 10 || phoneNumber.length != 11
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
}