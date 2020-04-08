package com.example.vinid_icecreams.ui.fragment.user.homeUser

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vinid_icecreams.base.viewmodel.BaseViewModel
import com.example.vinid_icecreams.model.User
import com.example.vinid_icecreams.repository.Repository
import com.example.vinid_icecreams.utils.Const.CODE_200
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user


    @SuppressLint("CheckResult")
    fun getUserProfile() {
        repository.callRequestUserProfile()
            ?.doOnSubscribe { isLoading.value = true }
            ?.doFinally { isLoading.value = false }
            ?.subscribe({ result ->
            when (result.meta?.code) {
                CODE_200 -> {
                    _user.value = result?.data
                }
                else -> {
                    messageFailed.value = result?.meta?.message
                }
            }
        }) { error ->
            messageFailed.value = error?.toString()
        }
    }

    fun removeToken(){
        repository.saveToken(null)
    }
}