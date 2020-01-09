package com.example.vinid_icecreams.connection

import com.example.vinid_icecreams.connection.body.AuthenBody
import com.example.vinid_icecreams.model.*
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*


interface APIService {

    @POST("/register")
    fun registerAccount(@Body body: AuthenBody): Single<MyResponse<DataUserResponse>>

    @POST("/login")
    fun authenticateAccount(@Body body: AuthenBody): Single<MyResponse<DataUserResponse>>

    @GET("/stores")
    fun getListStore(): Single<MyResponse<ArrayList<Store>>>

    @GET("/stores/{id}/items")
    fun getListIceCream(@Path("id") storeID: Int): Single<MyResponse<ArrayList<IceCream>>>

    @POST("")
    fun payOrderUser(
        @Query("listOrder") listOrder: ArrayList<Order>
        , @Query("total") total: Int
    ): Single<Response<Boolean>>
}