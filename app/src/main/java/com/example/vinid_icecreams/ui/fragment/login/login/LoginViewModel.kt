package com.example.vinid_icecreams.ui.fragment.login.login

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vinid_icecreams.base.viewmodel.BaseViewModel
import com.example.vinid_icecreams.repository.Repository
import com.example.vinid_icecreams.viewmodel.ViewModelIceCream
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    private val _isLoginSuccess = MutableLiveData<Boolean>()
    val isLoginSuccess: LiveData<Boolean> get() = _isLoginSuccess

    @SuppressLint("CheckResult")
    fun handleLogin(phoneNumber: String, password: String) {
        if (checkPhoneNumber(phoneNumber) && checkPassWord(password)) {
            repository.callLoginAccount(phoneNumber, password)?.subscribe({ result ->
                    when (result.meta?.code) {
                        ViewModelIceCream.CODE_200 -> {
                            /*post data to view */
                            messageSuccess.value = result?.meta?.message
                            _isLoginSuccess.value = true
                            /*save token*/
                            repository.saveToken(result.data?.token)
                        }
                        else -> {
                            /*handle login failse*/
                            messageFailed.value = result?.meta?.message
                            _isLoginSuccess.value = false
                        }
                    }
            }) { error ->
                messageFailed.value = error.toString()
                    _isLoginSuccess.value = false
            }
        } else {
            messageFailed.value = VERIFY_FAIL
            _isLoginSuccess.value = false
            return
        }
    }

    private fun checkPassWord(password: String): Boolean {
        if (password.isEmpty()) {
            return false
        }
        return password.length >= 8
    }

    private fun checkPhoneNumber(phoneNumber: String): Boolean {
        if (phoneNumber.isEmpty()) {
            return false
        }
        return android.util.Patterns.PHONE.matcher(phoneNumber).matches()
    }

    companion object{
        const val VERIFY_FAIL = "Vui lòng kiểm tra lại thông tin"
    }
}