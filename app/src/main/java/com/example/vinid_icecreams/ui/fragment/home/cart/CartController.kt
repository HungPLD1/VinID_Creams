package com.example.vinid_icecreams.ui.fragment.home.cart

import com.airbnb.epoxy.EpoxyController
import com.example.vinid_icecreams.model.Order
import com.example.vinid_icecreams.ui.activity.home.HomeViewModel
import com.example.vinid_icecreams.ui.fragment.home.cart.model.CartItemHolder
import com.example.vinid_icecreams.ui.fragment.home.cart.model.cartItemHolder

class CartController(
    private val mainViewModel: HomeViewModel,
    private val showDialog: (index :Int) -> Unit,
    private val showTotal : (totalPrice : Int) -> Unit
) : EpoxyController() {


    var listOrder: ArrayList<Order>? = null
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        listOrder?.forEachIndexed { index, order ->
            var count = order.amount
            cartItemHolder {
                id(CartItemHolder.TAG)
                order(order)
                count(order.amount)
                cartItemListener(object : CartItemHolder.Listener {
                    override fun onInsertTotal() {
                        insertTotal()
                    }
                    override fun onClickPlus() {
                        if (count < 99) {
                            count += 1
                            order.amount = count
                            order.total = order.iceCream.price!! * count
                            insertTotal()
                        }
                    }

                    override fun onClickMinus() {
                        if (count > 1) {
                            count -= 1
                            order.amount = count
                            order.total = order.iceCream.price!! * count
                            insertTotal()
                            cartItemHolder {
                                id(CartItemHolder.TAG)
                                order(order)
                                count(order.amount)
                            }
                        } else {
                            showDialog(index)
                        }
                    }

                })
            }
        }
    }

    private fun insertTotal() {
        val totalPrice = ArrayList<Int>()
        mainViewModel.getListOrder().forEach {
            totalPrice.add(it.total)
        }

        showTotalOnView(totalPrice)
    }

    private fun showTotalOnView(totalPrice: ArrayList<Int>) {
        var total = 0
        totalPrice.forEach { total += it }
        showTotal(total)
    }
}