package com.example.vinid_icecreams.ui.fragment.home.store

import com.airbnb.epoxy.EpoxyController
import com.example.vinid_icecreams.model.Store
import com.example.vinid_icecreams.ui.fragment.home.store.model.storeHolderView

class StoreController(
    private val toShopping: (index :Int) -> Unit
) : EpoxyController() {

    var listStore: ArrayList<Store>? = null
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        listStore?.forEachIndexed { index, store ->
            storeHolderView {
                id(store.id)
                store(store)
                onClickStoreListener { toShopping(index) }
            }
        }
    }

}