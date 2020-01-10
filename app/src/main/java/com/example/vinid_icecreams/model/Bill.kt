package com.example.vinid_icecreams.model

import com.google.gson.annotations.SerializedName

data class Bill( var status: Int?,
                 var  address: Address?,
                 var shipFee: Double?,
                 var totalFree: Int?,
                 var listItem: ArrayList<Item>?)

data class Address(
    @SerializedName("latitude")
    var latitude: Double?,
    @SerializedName("longitude")
    var longitude: Double?
)

data class Item(
    @SerializedName("item_id")
    var itemID: Int?,
    @SerializedName("quantity")
    var quantity: Int?
)

