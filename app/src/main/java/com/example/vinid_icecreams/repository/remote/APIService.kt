package com.example.vinid_icecreams.repository.remote

import com.example.vinid_icecreams.repository.remote.requestBody.AuthenRequest
import com.example.vinid_icecreams.repository.remote.requestBody.BillRequest
import com.example.vinid_icecreams.repository.remote.requestBody.PointRequest
import com.example.vinid_icecreams.repository.remote.requestBody.RatingRequest
import com.example.vinid_icecreams.model.*
import io.reactivex.Single
import retrofit2.http.*


interface APIService {

    @POST("/register")
    fun registerAccount(@Body body: AuthenRequest): Single<MyResponse<DataUser>>

    @POST("/login")
    fun authenticateAccount(@Body body: AuthenRequest): Single<MyResponse<DataUser>>

    @GET("/api/stores")
    fun getListStore(): Single<MyResponse<ArrayList<Store>>>

    @GET("/api/stores/{id}/items")
    fun getListIceCream(@Path("id") storeID: Int): Single<MyResponse<ArrayList<IceCream>>>

    @GET("/api/items/{id}")
    fun getDetailsIceCream(@Path("id") iceCreamID : Int): Single<MyResponse<IceCream>>

    @GET("/api/notification")
    fun getNotification(): Single<MyResponse<ArrayList<Event>>>

    @POST("api/orders")
    fun payOrderUser(@Body body: BillRequest): Single<MyResponse<Bill>>

    @GET("/api/users/orders")
    fun getOrderUser(): Single<MyResponse<ArrayList<OrderInfor>>>

    @GET("/api/orders/{id}")
    fun getDetailsOrder(@Path ("id") orderID:Int): Single<MyResponse<Bill>>

    @POST("/api/ratings")
    fun setRatingForItem(@Body body: RatingRequest): Single<MyResponse<Rating>>

    @GET("/api/users/detail")
    fun getUserProfile() : Single<MyResponse<User>>

    @PUT("/api/users/deposit")
    fun chargePointUser(@Body body:PointRequest) : Single<MyResponse<User>>
}