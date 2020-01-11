package com.example.vinid_icecreams.repository

import android.annotation.SuppressLint
import com.example.vinid_icecreams.connection.RetrofitIceCream
import com.example.vinid_icecreams.connection.body.AuthenBody
import com.example.vinid_icecreams.connection.body.Bill
import com.example.vinid_icecreams.model.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class Repository {

    companion object {
        val mInstance = Repository()
        val TAG = Repository::class.java.name
    }

    @SuppressLint("CheckResult")
    fun callLoginAccount(
        mPhoneNumber: String,
        mPassword: String
    ): Single<MyResponse<DataUserResponse>>? {
        return RetrofitIceCream.createRetrofit()
            ?.authenticateAccount(AuthenBody(mPhoneNumber, mPassword))
            ?.observeOn(AndroidSchedulers.mainThread())?.subscribeOn(Schedulers.io())
    }

    @SuppressLint("CheckResult")
    fun callRegisterAccount(
        mPhoneNumber: String,
        mPassword: String
    ): Single<MyResponse<DataUserResponse>>? {
        val body = AuthenBody(mPhoneNumber, mPassword)
        return RetrofitIceCream.createRetrofit()?.registerAccount(body)
            ?.observeOn(AndroidSchedulers.mainThread())?.subscribeOn(Schedulers.io())
    }


    @SuppressLint("CheckResult")
    fun callRequestListStore(): Single<MyResponse<ArrayList<Store>>>? {
        return RetrofitIceCream.createRetrofit()?.getListStore()
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeOn(Schedulers.io())
    }

    @SuppressLint("CheckResult")
    fun callRequestListIceCream(storeID: Int): Single<MyResponse<ArrayList<IceCream>>>? {
        return RetrofitIceCream.createRetrofit()?.getListIceCream(storeID)
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeOn(Schedulers.io())
    }

    @SuppressLint("CheckResult")
    fun callPayIceCream(bill: Bill): Single<MyResponse<BillResponse>>? {
        return RetrofitIceCream.createRetrofit()?.payOrderUser(bill)
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeOn(Schedulers.io())
    }
}