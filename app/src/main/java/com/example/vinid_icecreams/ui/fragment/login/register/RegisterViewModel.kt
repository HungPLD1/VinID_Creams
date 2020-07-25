package com.example.vinid_icecreams.ui.fragment.login.register

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vinid_icecreams.base.viewmodel.BaseViewModel
import com.example.vinid_icecreams.extension.add
import com.example.vinid_icecreams.extension.applySchedulersSingle
import com.example.vinid_icecreams.repository.Repository
import com.example.vinid_icecreams.utils.Const.CODE_200
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    private val _isRegisterSuccess = MutableLiveData<Boolean>()
    val isRegisterSuccess: LiveData<Boolean> get() = _isRegisterSuccess

    @SuppressLint("CheckResult")
    fun handleRegister(phoneNumber: String, password: String, passwordRepeat: String) {
        if (checkPhoneNumber(phoneNumber) && handleComparedPassword(password, passwordRepeat)) {
            repository.callRegisterAccount(phoneNumber, password)
                ?.compose(applySchedulersSingle(isLoading))
                ?.subscribe({ result ->
                    when (result.meta?.code) {
                        CODE_200 -> {
                            messageSuccess.value = result?.meta?.message
                            _isRegisterSuccess.value = true
                            /*save token*/
                            repository.saveToken(result.data?.token)
                        }
                        else -> {
                            messageFailed.value = result?.meta?.message
                            _isRegisterSuccess.value = false
                        }
                    }
                }) { error ->
                    messageFailed.value = error.toString()
                    _isRegisterSuccess.value = false
                }?.add(this)
        } else {
            messageFailed.value = RegisterViewModel.REGISTER_FAIL
            _isRegisterSuccess.value = false
            return
        }
    }

    private fun checkPhoneNumber(phoneNumber: String): Boolean {
        if (phoneNumber.isEmpty()) {
            return false
        }
        return android.util.Patterns.PHONE.matcher(phoneNumber).matches()
    }

    private fun handleComparedPassword(password: String, passwordRepeat: String): Boolean {
        if (password.isEmpty() || passwordRepeat.isEmpty()) {
            return false
        }
        if (password.length < 8 || passwordRepeat.length < 8) {
            return false
        }
        return password == passwordRepeat
    }

    companion object {
        const val REGISTER_FAIL = "Đăng ký không thành công"
    }
}