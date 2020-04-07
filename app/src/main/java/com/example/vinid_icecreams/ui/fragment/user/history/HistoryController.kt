package com.example.vinid_icecreams.ui.fragment.user.history

import com.airbnb.epoxy.EpoxyController
import com.example.vinid_icecreams.model.OrderInfor
import com.example.vinid_icecreams.ui.fragment.user.history.model.orderHistoryItemView

class HistoryController(
    private val toDetailsHistory: (index :Int) -> Unit
) : EpoxyController() {

    var listOrderInfor: ArrayList<OrderInfor>? = null
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        listOrderInfor?.forEachIndexed { index, orderInfor ->
            orderHistoryItemView {
                id(orderInfor.id)
                orderInfor(orderInfor)
                onClickDetailsListener {toDetailsHistory(index) }
            }
        }
    }

}