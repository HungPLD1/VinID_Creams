package com.example.vinid_icecreams.repository.remote

import com.google.gson.annotations.SerializedName

data class MyResponse <T> (
    @SerializedName("data")
    var data : T?,
    @SerializedName("meta")
    var meta : Meta?
)


data class Meta(
    @SerializedName("code")
    var code : Int?,
    @SerializedName("message")
    var message: String?
)