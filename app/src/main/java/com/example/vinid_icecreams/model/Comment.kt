package com.example.vinid_icecreams.model

import com.google.gson.annotations.SerializedName


data class Comment(
    @SerializedName("user_name")
    var user_name: String,
    @SerializedName("user_avatar")
    var user_avatar: String,
    @SerializedName("rating_star")
    var rating_star : Int,
    @SerializedName("comment")
    var comment :String
)