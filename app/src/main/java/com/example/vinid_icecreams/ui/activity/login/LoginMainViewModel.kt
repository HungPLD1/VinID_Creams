package com.example.vinid_icecreams.ui.activity.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vinid_icecreams.base.viewmodel.BaseViewModel
import com.example.vinid_icecreams.repository.Repository
import timber.log.Timber
import javax.inject.Inject

class LoginMainViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    private val _isHaveToken = MutableLiveData<Boolean>()
    val isHaveToken: LiveData<Boolean> get() = _isHaveToken


    fun checkIsLogin() {
        val token = repository.getToken()
        Timber.d(token)
        _isHaveToken.value = !token.isNullOrEmpty()
    }
}