package com.example.vinid_icecreams.model

import com.google.gson.annotations.SerializedName

data class Event (
    @SerializedName("id")
    var mId :Int,
    @SerializedName("image_path")
    var mImage : String,
    @SerializedName("title")
    var mTitle : String,
    @SerializedName("content")
    var mContent : String
)