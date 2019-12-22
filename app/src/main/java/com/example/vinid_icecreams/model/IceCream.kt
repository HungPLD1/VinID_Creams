package com.example.vinid_icecreams.model

import java.sql.Timestamp

data class IceCream(
    var id: Int,
    var name: String,
    var type: String,
    var imagePath: String,
    var price: Int,
    var discount: String,
    var createdAt: Timestamp
)