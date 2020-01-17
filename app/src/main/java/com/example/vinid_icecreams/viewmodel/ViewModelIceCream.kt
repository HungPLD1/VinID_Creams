package com.example.vinid_icecreams.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vinid_icecreams.connection.body.Bill
import com.example.vinid_icecreams.connection.body.Point
import com.example.vinid_icecreams.connection.body.Rating
import com.example.vinid_icecreams.model.*
import com.example.vinid_icecreams.repository.Repository

class ViewModelIceCream : ViewModel() {
    var mListStore = MutableLiveData<ArrayList<Store>>()
    var mListIceCream = MutableLiveData<ArrayList<IceCream>>()
    var mListEvent = MutableLiveData<ArrayList<Event>>()
    var mIceCream = MutableLiveData<IceCream>()
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
                        mMessageSuccess.postValue(result?.meta?.message)
                        mListStore.postValue(result.data)
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
                            mMessageSuccess.postValue(result?.meta?.message)
                            mIsRequestRegister.postValue(true)
                            /*post token*/
                            mToken.postValue(result.data?.token)
                        }
                        else -> {
                            mMessageFailse.postValue(result?.meta?.message)
                            mIsRequestRegister.postValue(false)
                        }
                    }
                }
            }) { error ->
                run {
                    Log.d(TAG, error.toString())
                    mMessageFailse.postValue(error.toString())
                    mIsRequestRegister.postValue(false)
                }
            }
        } else {
            mMessageFailse.postValue(REGISTER_FAILSE)
            mIsRequestRegister.postValue(false)
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
                            mMessageSuccess.postValue(result?.meta?.message)
                            mIsRequestLogin.postValue(true)
                            /*post token*/
                            mToken.postValue(result.data?.token)
                            Log.d(TAG, result.data?.token.toString())
                        }
                        else -> {
                            /*handle login failse*/
                            mMessageFailse.postValue(result?.meta?.message)
                            mIsRequestLogin.postValue(false)
                        }
                    }
                }
            }) { error ->
                run {
                    mMessageFailse.postValue(error.toString())
                    mIsRequestLogin.postValue(false)
                }
            }
        } else {
            mMessageFailse.postValue(VERIFY_FAILSE)
            mIsRequestLogin.postValue(false)
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
                        mMessageSuccess.postValue(result?.meta?.message)
                        mIsPayment.postValue(true)
                    }
                    else -> {
                        mMessageFailse.postValue(result?.meta?.message)
                        mIsPayment.postValue(false)
                    }
                }
            }
        }) { error ->
            run {
                mMessageFailse.postValue(error.toString())
                mIsPayment.postValue(false)
            }
        }
    }

    @SuppressLint("CheckResult")
    fun getOrderUser() {
        Repository.mInstance.callRequestOrderUser()?.subscribe({ result ->
            run {
                when (result.meta?.code) {
                    CODE_200 -> {
                        mListOrderInfor.postValue(result?.data)
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
    fun getUserProfile() {
        Repository.mInstance.callRequestUserProfile()?.subscribe({ result ->
            run {
                when (result.meta?.code) {
                    CODE_200 -> {
                        mUser.postValue(result?.data)
                    }
                    else -> {
                        mMessageFailse.postValue(result?.meta?.message)
                    }
                }
            }
        }) { error ->
            run {
                Log.d(TAG,error.toString())
            }
        }
    }

    @SuppressLint("CheckResult")
    fun getListItemInfo(orderID : Int) {
        Repository.mInstance.callRequestDetailsOrder(orderID)?.subscribe({ result ->
            run {
                when (result.meta?.code) {
                    CODE_200 -> {
                        mListItemOrder.postValue(result.data?.mListItems)
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
    fun setRatingItem(itemID : Int? , ratingStar: Int? ,comment :String?) {
        val bodyRating = Rating(itemID,ratingStar,comment)
        Repository.mInstance.callRequestSetRating(bodyRating)?.subscribe({ result ->
            run {
                when (result.meta?.code) {
                    CODE_200 -> {
                        mIsRating.postValue(true)
                    }
                    else -> {
                        mIsRating.postValue(false)
                        mMessageFailse.postValue(result?.meta?.message)
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
    fun setPointUser(amount :Int) {
            Repository.mInstance.callRequestChargePoint(Point(amount))?.subscribe({ result ->
                run {
                    when (result.meta?.code) {
                        CODE_200 -> {
                            mIsChargePoint.postValue(true)
                            mUser.postValue(result.data)
                        }
                        else -> {
                            mIsChargePoint.postValue(false)
                            mMessageFailse.postValue(result.meta?.message)
                        }
                    }
                }
            }) { error ->
                run {
                    mIsChargePoint.postValue(false)
                    mMessageFailse.postValue(error.toString())
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
        super.onCleared()
    }
}