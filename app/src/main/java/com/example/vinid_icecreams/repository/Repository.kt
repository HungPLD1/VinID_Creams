package com.example.vinid_icecreams.repository

import android.annotation.SuppressLint
import android.util.Log
import com.example.vinid_icecreams.connection.RetrofitIceCream
import com.example.vinid_icecreams.model.IceCream
import com.example.vinid_icecreams.model.Store
import com.example.vinid_icecreams.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class Repository {
    //success
    private val CODE_200 = 200
    private val TAG = Repository::class.java.name

    companion object {
        val mInstance = Repository()
    }

    @SuppressLint("CheckResult")
    fun callLoginAccount(mPhoneNumber: Int, mPassword: String) {
        RetrofitIceCream.createRetrofit()?.authenticateAccount(mPhoneNumber, mPassword)
            ?.observeOn(AndroidSchedulers.mainThread())?.subscribeOn(Schedulers.io())
            ?.subscribe({ result ->
                when (result.meta?.code) {
                    CODE_200 ->{

                    }
                }
            }) { error ->
            }
    }

    @SuppressLint("CheckResult")
    fun callRegisterAccount(mPhoneNumber: Int, mPassword: String, callback: OnRespone<User, String>) {
        RetrofitIceCream.createRetrofit()?.authenticateAccount(mPhoneNumber, mPassword)
            ?.observeOn(AndroidSchedulers.mainThread())?.subscribeOn(Schedulers.io())
            ?.subscribe({ result ->
                when (result.meta?.code) {
                    CODE_200 ->{
                        result?.data?.user?.let { callback.onSuccess(it) }
                        val token = result.data?.token
                        Log.d(TAG,token.toString())
                    }
                }
            }) { error ->

            }
    }


    @SuppressLint("CheckResult")
    fun callRequestListStore(callback: OnRespone<ArrayList<Store>, String>) {
        RetrofitIceCream.createRetrofit()?.getListStore()?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeOn(Schedulers.io())
            ?.subscribe({ result ->
                when (result.meta?.code) {
                    CODE_200 -> {
                        result.data?.let { callback.onSuccess(it) }
                    }
                }
            }) { error ->
                callback.onFailse(error.toString())
            }
    }

    @SuppressLint("CheckResult")
    fun callRequestListIceCream(storeID: Int, callback: OnRespone<ArrayList<IceCream>, String>) {
        RetrofitIceCream.createRetrofit()?.getListIceCream(storeID)
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeOn(Schedulers.io())
            ?.subscribe({ result ->
                when (result.meta?.code) {
                    CODE_200 -> {
                        result.data?.let { callback.onSuccess(it) }
                    }
                }
            }) { error ->
                Log.d(TAG, error.toString())
                callback.onFailse(error.toString())
            }
    }

}