package com.example.vinid_icecreams.connection.body

import com.google.gson.annotations.SerializedName

data class Rating (
    @SerializedName("item_id")
    var itemID : Int?,
    @SerializedName("rating_star")
    var ratingStar : Int?,
    @SerializedName("comment")
    var comment : String?
)