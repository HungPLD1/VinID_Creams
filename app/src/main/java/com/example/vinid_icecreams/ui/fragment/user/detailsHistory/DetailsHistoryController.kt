package com.example.vinid_icecreams.ui.fragment.user.detailsHistory

import com.airbnb.epoxy.EpoxyController
import com.example.vinid_icecreams.model.ItemOrder
import com.example.vinid_icecreams.ui.fragment.user.detailsHistory.model.ItemOrderView
import com.example.vinid_icecreams.ui.fragment.user.detailsHistory.model.itemOrderView


class DetailsHistoryController(
    private val submitRating:(id: Int, starRating: Int, comment: String) -> Unit
) : EpoxyController() {

    var listItemOrder: ArrayList<ItemOrder>? = null
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        listItemOrder?.forEach {
            itemOrderView {
                id(ItemOrderView.TAG)
                itemOrder(it)
                listener(object : ItemOrderView.ItemOrderListener{
                    override fun onSubmit(id: Int, starRating: Int, comment: String) {
                        submitRating(id,starRating,comment)
                    }
                })
            }
        }
    }

}