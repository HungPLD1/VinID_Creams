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

    @GET("/api/items/{id}")
    fun getDetailsIceCream(@Path("id") iceCreamID : Int): Single<MyResponse<IceCream>>

    @GET("/api/notification")
    fun getNotification(): Single<MyResponse<ArrayList<Event>>>

    @POST("api/orders")
    fun payOrderUser(@Body body: Bill): Single<MyResponse<BillResponse>>

    @GET("/api/users/orders")
    fun getOrderUser(): Single<MyResponse<ArrayList<OrderInfor>>>

    @GET("/api/orders/{id}")
    fun getDetailsOrder(@Path ("id") orderID:Int): Single<MyResponse<BillResponse>>
}