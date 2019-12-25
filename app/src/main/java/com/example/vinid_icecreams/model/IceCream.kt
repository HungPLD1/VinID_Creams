package com.example.vinid_icecreams.model

data class IceCream(
    var id: Int,
    var name: String,
    var type: String,
    var imagePath: String,
    var price: Int,
    var discount: Int,
    var createdAt: Long
)