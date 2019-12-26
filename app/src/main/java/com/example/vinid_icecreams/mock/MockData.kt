package com.example.vinid_icecreams.mock

import com.example.vinid_icecreams.model.IceCream
import com.example.vinid_icecreams.model.Store
import com.example.vinid_icecreams.model.User

object MockData {
    fun getListStore(): ArrayList<Store> {
        val mListStore = ArrayList<Store>()
        for (i in 1..10) {
            val mMockStore = Store(
                i,
                "Cửa hàng thứ $i" ,
                "Hà nội",
                123213,
                31231,
                0,
                312312312312,
                getListCream()
            )
            mListStore.add(mMockStore)
        }
        return mListStore
    }

    fun getListCream(): ArrayList<IceCream> {
        val mListCream = ArrayList<IceCream>()
        for (i in 1..20) {
            val mMockIcream = IceCream(
                i,
                "Kem dâu",
                5,
                "https://cdn.apartmenttherapy.info/image/fetch/f_jpg,q_auto:eco,c_fill,g_auto,w_1500,ar_1:1/https%3A%2F%2Fstorage.googleapis.com%2Fgen-atmedia%2F3%2F2014%2F06%2F04a3c9323983d070e1177a0c6300d1afce24ae04.jpeg",
                100,
                20,
                3.5f,
                31263712836
            )
            mListCream.add(mMockIcream)
        }
        return mListCream
    }

    fun getUser(): User {
        return User(
            0,
            "pham le duy hung",
            "hung",
            "hungpldgc1994@gmail.com",
            981438710,
            "hung0894",
            "Hà Nội",
            2000,
            0,
            0,
            3123124124214
        )
    }
}