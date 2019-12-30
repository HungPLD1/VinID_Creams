package com.example.vinid_icecreams.connection

import com.example.vinid_icecreams.model.IceCream
import com.example.vinid_icecreams.model.Order
import com.example.vinid_icecreams.model.Store
import com.example.vinid_icecreams.model.User
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
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

    @GET("")
    fun getListStore(): Observable<Response<ArrayList<Store>>>

    @GET("")
    fun getListStoreWithCurrentLocation(
        @Query("longitude") longitude: Int
        , @Query("latitude") latitude: Int
    ): Observable<Response<ArrayList<Store>>>

    @GET("")
    fun getListIceCream(@Query("storeID") storeID: Int): Observable<Response<ArrayList<IceCream>>>

    @POST("")
    fun payOrderUser(
        @Query("listOrder") listOrder: ArrayList<Order>
        , @Query("total") total: Int
    ): Observable<Response<Boolean>>
}