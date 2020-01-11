package com.example.vinid_icecreams.connection.body

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Bill(
    @SerializedName("status")
    var status: Int?,
    @SerializedName("coordinates")
    var  coordinates: Coordinates?,
    @SerializedName("ship_fee")
    var shipFee: Int?,
    @SerializedName("total_fee")
    var totalFree: Int?,
    @SerializedName("items")
    var listItem: ArrayList<Item>?
) : Serializable

data class Coordinates(
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

