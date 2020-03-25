package com.example.vinid_icecreams.model

import com.google.gson.annotations.SerializedName

data class Rating (
    @SerializedName("id")
    var id :Int?,
    @SerializedName("rating_star")
    var ratingStar :Int?,
    @SerializedName("comment")
    var comment: String?,
    @SerializedName("item_id")
    var itemId : Int?,
    @SerializedName("user_id")
    var userID : Int?,
    @SerializedName("created_at")
    var createdAt : String
)