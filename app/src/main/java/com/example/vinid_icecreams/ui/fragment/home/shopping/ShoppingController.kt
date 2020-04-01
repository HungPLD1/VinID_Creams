package com.example.vinid_icecreams.ui.fragment.home.shopping

import com.airbnb.epoxy.EpoxyController
import com.example.vinid_icecreams.model.IceCream
import com.example.vinid_icecreams.ui.fragment.home.shopping.model.IceCreamHolderView
import com.example.vinid_icecreams.ui.fragment.home.shopping.model.iceCreamHolderView
import timber.log.Timber
import kotlin.collections.ArrayList

class ShoppingController(
    private val toDetailsIceCream: (index :Int) -> Unit
) : EpoxyController() {

    var listIceCream: ArrayList<IceCream>? = null
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        Timber.d(listIceCream?.size.toString())
        listIceCream?.forEachIndexed { index, iceCream ->
            iceCreamHolderView {
                id(iceCream.id)
                iceCream(iceCream)
                onClickStoreListener {
                    toDetailsIceCream(index)
                }
                spanSizeOverride { totalSpanCount, _, _ ->
                    totalSpanCount / ITEM_PER_ROW
                }
            }
        }
    }

    /*handle search item in list ice cream*/
    fun filter( input: String) {
        var text = input
        val listCopyIceCream: ArrayList<IceCream> = ArrayList()
        listIceCream?.let { listCopyIceCream.addAll(it) }
        listIceCream?.clear()
        if (text.isEmpty()) {
            listIceCream?.addAll(listCopyIceCream)
        } else {
            text = text.toLowerCase()
            for (iceCream in listCopyIceCream) {
                if (iceCream.name!!.toLowerCase().contains(text)) {
                    listIceCream?.add(iceCream)
                }
            }
        }
        requestModelBuild()
    }

    companion object {
        internal const val ITEM_PER_ROW = 2
    }
}