package com.example.vinid_icecreams.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vinid_icecreams.model.Event
import com.example.vinid_icecreams.model.IceCream
import com.example.vinid_icecreams.model.Store
import com.example.vinid_icecreams.model.User
import com.example.vinid_icecreams.repository.OnRespone
import com.example.vinid_icecreams.repository.Repository
import com.example.vinid_icecreams.utils.ProgressLoading

class ViewModelIceCream : ViewModel() {
    var mListStore = MutableLiveData<ArrayList<Store>>()
    var mListIceCream = MutableLiveData<ArrayList<IceCream>>()
    var mListEvent = MutableLiveData<ArrayList<Event>>()
    var mIsRequestLogin = MutableLiveData<Boolean>()

    companion object{
        val TAG = ViewModelIceCream::class.java.name
        const val CODE_200 = 200
    }

    @SuppressLint("CheckResult")
    fun getListStore() {
        Repository.mInstance.callRequestListStore()?.subscribe({result ->
            run {
                when (result.meta?.code) {
                   CODE_200 ->{
                       mListStore.postValue(result.data)
                   }
                }
            }
        }){
                error ->
            run {

            }
        }
    }

    @SuppressLint("CheckResult")
    fun getListIceCream(storeID: Int) {
        Repository.mInstance.callRequestListIceCream(storeID)?.subscribe({result ->
            run {
                when (result.meta?.code) {
                    CODE_200 ->{
                        mListIceCream.postValue(result.data)
                    }
                }
            }
        }){
                error ->
            run {

            }
        }
    }

    @SuppressLint("CheckResult")
    fun handleRegister(phoneNumber : Int, password : String) {
        Repository.mInstance.callRegisterAccount(phoneNumber,password)?.subscribe({result ->
            run {
                when (result.meta?.code) {
                    CODE_200 ->{

                    }
                }
            }
        }){
                error ->
            run {

            }
        }
    }





}