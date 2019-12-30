package com.example.vinid_icecreams.repository

import android.annotation.SuppressLint
import com.example.vinid_icecreams.connection.RetrofitIceCream
import com.example.vinid_icecreams.model.User
import com.example.vinid_icecreams.utils.ProgressLoading
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class Repository {
    //success
    val CODE_200 = 200
    //success when created
    val CODE_201 = 201
    // success but no respone
    val CODE_204 = 204
    // bad request
    val CODE_400 = 400
    // bad request with Unauthorized
    val CODE_401 = 401
    // not found
    val CODE_404 = 404



    companion object{
        val mInstance = Repository()
    }

    @SuppressLint("CheckResult")
    fun callAuthenticateAccount(mPhoneNumber : Int, mPassword : String): User?{
        val mUser : User? = null
        RetrofitIceCream.createRetrofit()!!.authenticateAccount(mPhoneNumber,mPassword).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({ result ->
                when(result.code()){
                    CODE_200,CODE_201,CODE_204 ->{
                        ProgressLoading.dismiss()
                    }
                    CODE_400,CODE_401,CODE_404 ->{
                        ProgressLoading.dismiss()
                    }
                }
            }){
                error ->
            }
        return mUser
    }
}