package com.example.vinid_icecreams.repository

import android.location.Location
import com.example.vinid_icecreams.repository.remote.APIService
import com.example.vinid_icecreams.repository.remote.requestBody.AuthenRequest
import com.example.vinid_icecreams.repository.remote.requestBody.Bill
import com.example.vinid_icecreams.repository.remote.requestBody.PointRequest
import com.example.vinid_icecreams.repository.remote.requestBody.RatingRequest
import com.example.vinid_icecreams.model.*
import com.example.vinid_icecreams.repository.local.ILocalDataSource
import com.example.vinid_icecreams.repository.remote.MyResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class  Repository @Inject constructor(
    private val apiService: APIService,
    private val iLocalDataSource: ILocalDataSource
) {

    fun callLoginAccount(
        mPhoneNumber: String,
        mPassword: String
    ): Single<MyResponse<DataUser>>? {
        return apiService.authenticateAccount(AuthenRequest(mPhoneNumber, mPassword))
            .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
    }

    fun callRegisterAccount(
        mPhoneNumber: String,
        mPassword: String
    ): Single<MyResponse<DataUser>>? {
        val body = AuthenRequest(mPhoneNumber, mPassword)
        return apiService.registerAccount(body)
            .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
    }

    fun callRequestListStore(): Single<MyResponse<ArrayList<Store>>>? {
        return apiService.getListStore()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun callRequestListIceCream(storeID: Int): Single<MyResponse<ArrayList<IceCream>>>? {
        return apiService.getListIceCream(storeID)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }


    fun callRequestDetailsIceCream(iceCreamID: Int): Single<MyResponse<IceCream>>? {
        return apiService.getDetailsIceCream(iceCreamID)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun callPayIceCream(bill: Bill): Single<MyResponse<com.example.vinid_icecreams.model.Bill>>? {
        return apiService.payOrderUser(bill)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun callRequestNotification(): Single<MyResponse<ArrayList<Event>>>?{
        return apiService.getNotification()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun callRequestOrderUser() : Single<MyResponse<ArrayList<OrderInfor>>>?{
        return apiService.getOrderUser()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun callRequestDetailsOrder(orderID : Int) :  Single<MyResponse<com.example.vinid_icecreams.model.Bill>>?{
        return apiService.getDetailsOrder(orderID)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun callRequestSetRating(ratingRequestBody : RatingRequest) : Single<MyResponse<Rating>>?{
        return apiService.setRatingForItem(ratingRequestBody)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun callRequestUserProfile() : Single<MyResponse<User>>?{
        return apiService.getUserProfile()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun callRequestChargePoint(pointRequest :PointRequest) : Single<MyResponse<User>>?{
        return apiService.chargePointUser(pointRequest)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun saveToken (token : String?){
        Timber.d(token)
        iLocalDataSource.saveToken(token)
    }

    fun getToken ():String?{
        return iLocalDataSource.getToken()
    }

    fun saveListOrder(listOrder: ArrayList<Order>) {
        iLocalDataSource.saveListOrder(listOrder)
    }

    fun getListOrder() = iLocalDataSource.getListOrder()

    fun saveLocation(location : Location){
        iLocalDataSource.saveLocation(location)
    }

    fun getLocation():Location? = iLocalDataSource.getLocation()
}