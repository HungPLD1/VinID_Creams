package com.example.vinid_icecreams.connection

import com.example.vinid_icecreams.connection.body.AuthenBody
import com.example.vinid_icecreams.connection.body.Bill
import com.example.vinid_icecreams.model.*
import io.reactivex.Single
import retrofit2.http.*


interface APIService {

    @POST("/register")
    fun registerAccount(@Body body: AuthenBody): Single<MyResponse<DataUserResponse>>

    @POST("/login")
    fun authenticateAccount(@Body body: AuthenBody): Single<MyResponse<DataUserResponse>>

    @GET("/api/stores")
    fun getListStore(): Single<MyResponse<ArrayList<Store>>>

    @GET("/api/stores/{id}/items")
    fun getListIceCream(@Path("id") storeID: Int): Single<MyResponse<ArrayList<IceCream>>>

    @POST("/order")
    fun payOrderUser(@Body body: Bill): Single<MyResponse<BillResponse>>
}