package com.example.vinid_icecreams.model

data class Store(
    var id:Int,
    var name:String,
    var address: String,
    var image_path :String,
    var latitude: Double,
    var longitude: Double,
    var createdAt: Long,
    var mListIceCream: ArrayList<IceCream>,
    var range : Double
)
