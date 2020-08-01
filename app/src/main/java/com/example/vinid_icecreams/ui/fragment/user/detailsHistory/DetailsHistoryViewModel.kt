package com.example.vinid_icecreams.ui.fragment.user.detailsHistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vinid_icecreams.base.viewmodel.BaseViewModel
import com.example.vinid_icecreams.extension.add
import com.example.vinid_icecreams.extension.applySchedulersSingle
import com.example.vinid_icecreams.model.ItemOrder
import com.example.vinid_icecreams.repository.Repository
import com.example.vinid_icecreams.repository.remote.requestBody.RatingRequest
import com.example.vinid_icecreams.utils.Const.CODE_200
import javax.inject.Inject

class DetailsHistoryViewModel @Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    private val _listItemOrder = MutableLiveData<ArrayList<ItemOrder>>()
    val listItemOrder: LiveData<ArrayList<ItemOrder>> get() = _listItemOrder

    private val _isRating = MutableLiveData<Boolean>()
    val isRating: LiveData<Boolean> get() = _isRating

    fun getListItemOrder(orderID: Int) {
        repository.callRequestDetailsOrder(orderID)
            ?.compose(applySchedulersSingle(loading))
            ?.subscribe({ result ->
            when (result.meta?.code) {
                CODE_200 -> {
                    _listItemOrder.value = result.data?.listItem
                }
                else -> {
                    messageFailed.value = result.meta?.message.toString()
                }
            }
        }) { error ->
            messageFailed.value = error.toString()
        }?.add(this)
    }

    fun setRatingItem(itemID: Int?, ratingStar: Int?, comment: String?) {
        val bodyRating = RatingRequest(itemID, ratingStar, comment)
        repository.callRequestSetRating(bodyRating)
            ?.compose(applySchedulersSingle(loading))
            ?.subscribe({ result ->
            when (result.meta?.code) {
                CODE_200 -> {
                    _isRating.value = true
                }
                else -> {
                    _isRating.value = false
                    messageFailed.value = result?.meta?.message
                }
            }
        }) { error ->
            _isRating.value = false
            messageFailed.value = error.toString()
        }?.add(this)
    }
}