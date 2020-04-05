package com.example.vinid_icecreams.model

import com.google.gson.annotations.SerializedName

data class Event (
    @SerializedName("id")
    var id :Int,
    @SerializedName("image_path")
    var image : String,
    @SerializedName("title")
    var title : String,
    @SerializedName("content")
    var content : String
)