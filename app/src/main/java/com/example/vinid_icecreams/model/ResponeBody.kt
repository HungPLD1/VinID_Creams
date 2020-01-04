package com.example.vinid_icecreams.model

import com.google.gson.annotations.SerializedName

data class ResponeBody <T> (
    @SerializedName("data")
    var mData : T,
    @SerializedName("meta")
    var mMeta : Meta
)


data class Meta(
    @SerializedName("code")
    var mCode : Int,
    @SerializedName("message")
    var mMesssage: String
)