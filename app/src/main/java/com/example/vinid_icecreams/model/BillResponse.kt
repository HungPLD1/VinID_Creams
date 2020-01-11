package com.example.vinid_icecreams.model

import com.example.vinid_icecreams.connection.body.Item
import com.google.gson.annotations.SerializedName

data class BillResponse (
        @SerializedName("order_info")
        var orderInfo : OrderInfor,
        @SerializedName("items")
        var mListItems: ArrayList<Item>
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