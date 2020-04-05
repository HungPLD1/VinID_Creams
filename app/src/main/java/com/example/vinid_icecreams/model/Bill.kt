package com.example.vinid_icecreams.model

import com.google.gson.annotations.SerializedName

data class Bill(
    @SerializedName("order_info")
    var orderInfo : OrderInfor,
    @SerializedName("items")
    var mListItems: ArrayList<ItemOrder>
)

data class OrderInfor(
    @SerializedName("id")
    var id : Int,
    @SerializedName("user_id")
    var userID : Int,
    @SerializedName("status")
    var status : Int,
    @SerializedName("delivery_address")
    var deliveryAddress: String,
    @SerializedName("ship_fee")
    var shipFee : Double,
    @SerializedName("total_fee")
    var totalFee : Double,
    @SerializedName("created_at")
    var createAt : String
)

data class ItemOrder(
    @SerializedName("item_info")
    var iceCreamInfo: ItemInfo,
    @SerializedName("quantity")
    var quantity : Int
)

data class ItemInfo(
    @SerializedName("id")
    var id : Int,
    @SerializedName("name")
    var name : String,
    @SerializedName("type")
    var type : String,
    @SerializedName("image_paths")
    var imagePath : ArrayList<String>,
    @SerializedName("price")
    var price : Int,
    @SerializedName("created_at")
    var createAt: String,
    @SerializedName("stores")
    var stores : Int,
    @SerializedName("ratings")
    var ratting : Int
)