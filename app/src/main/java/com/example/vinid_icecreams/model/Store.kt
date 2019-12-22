package com.example.vinid_icecreams.model

import java.sql.Timestamp

data class Store (
    var id:Int,
    var name :String,
    var address: String,
    var latitude: Int,
    var longitude : Int,
    var status : Int,
    var createdAt:Timestamp,
    var mListIceCream: ArrayList<IceCream>
)
