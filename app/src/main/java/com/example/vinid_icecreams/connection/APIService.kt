package com.example.vinid_icecreams.connection

import com.example.vinid_icecreams.model.*
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface APIService {

    @POST("")
    fun registerAccount(
        @Query("phoneNumber") phoneNumber: Int
        , @Query("password") password: String
    ): Observable<Response<Boolean>>

    @POST("")
    fun authenticateAccount(
        @Query("phoneNumber") phoneNumber: Int
        , @Query("password") password: String
    ): Observable<Response<User>>

    @GET("/stores")
    fun getListStore(): Single<Response<ResponeBody<ArrayList<Store>>>>

    @GET("/stores/{id}/items")
    fun getListIceCream(@Path("id")storeID : Int): Observable<Response<ResponeBody<ArrayList<IceCream>>>>

    @POST("")
    fun payOrderUser(
        @Query("listOrder") listOrder: ArrayList<Order>
        , @Query("total") total: Int
    ): Observable<Response<Boolean>>
}