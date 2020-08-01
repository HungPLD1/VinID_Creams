package com.example.vinid_icecreams.ui.fragment.user.wallet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vinid_icecreams.base.viewmodel.BaseViewModel
import com.example.vinid_icecreams.extension.add
import com.example.vinid_icecreams.extension.applySchedulersSingle
import com.example.vinid_icecreams.model.User
import com.example.vinid_icecreams.repository.Repository
import com.example.vinid_icecreams.repository.remote.requestBody.PointRequest
import com.example.vinid_icecreams.utils.Const.CODE_200
import javax.inject.Inject

class WalletViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    private val _isChargePoint = MutableLiveData<Boolean>()
    val isChargePoint: LiveData<Boolean> get() = _isChargePoint

    fun setPointUser(amount: Int) {
        repository.callRequestChargePoint(PointRequest(amount))
            ?.compose(applySchedulersSingle(loading))
            ?.subscribe({ result ->
                when (result.meta?.code) {
                    CODE_200 -> {
                        _isChargePoint.value = true
                        _user.value = result.data
                    }
                    else -> {
                        _isChargePoint.value = false
                        messageFailed.value = result.meta?.message
                    }
                }
            }) { error ->
                _isChargePoint.value = false
                messageFailed.value = error.toString()
            }?.add(this)
    }

    fun getUserProfile() {
        repository.callRequestUserProfile()
            ?.compose(applySchedulersSingle(loading))
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
            }?.add(this)
    }
}