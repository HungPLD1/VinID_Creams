package com.example.vinid_icecreams.repository.remote.requestBody

import com.google.gson.annotations.SerializedName

data class RatingRequest (
    @SerializedName("item_id")
    var itemID : Int?,
    @SerializedName("rating_star")
    var ratingStar : Int?,
    @SerializedName("comment")
    var comment : String?
)