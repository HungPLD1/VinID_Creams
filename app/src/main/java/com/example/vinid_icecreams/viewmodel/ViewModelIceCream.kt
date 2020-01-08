package com.example.vinid_icecreams.viewmodel

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
    var TAG = ViewModelIceCream::class.java.name
    var mListStore = MutableLiveData<ArrayList<Store>>()
    var mListIceCream = MutableLiveData<ArrayList<IceCream>>()
    var mListEvent = MutableLiveData<ArrayList<Event>>()
    var mIsRequestLogin = MutableLiveData<Boolean>()

    fun getListStore() {
        Repository.mInstance.callRequestListStore(object : OnRespone<ArrayList<Store>, String> {
            override fun onSuccess(value: ArrayList<Store>) {
                mListStore.postValue(value)
                ProgressLoading.dismiss()
            }

            override fun onFailse(value: String) {
                ProgressLoading.dismiss()
            }

        })
    }

    fun getListIceCream(storeID: Int) {
        Repository.mInstance.callRequestListIceCream(storeID,
            object : OnRespone<ArrayList<IceCream>, String> {
                override fun onSuccess(value: ArrayList<IceCream>) {
                    mListIceCream.postValue(value)
                    ProgressLoading.dismiss()
                }

                override fun onFailse(value: String) {
                    ProgressLoading.dismiss()
                }

            })
    }

    fun handleRegister(phoneNumber : Int ,password : String) {
        Repository.mInstance.callRegisterAccount(phoneNumber,password ,object : OnRespone<User,String>{
            override fun onSuccess(value: User) {
                /*save data user to share preferent */
            }
            override fun onFailse(value: String) {

            }

        })
    }





}