package com.example.vinid_icecreams.model



data class ResponeBody (
    var mData : Any,
    var mMeta : Meta
)


data class Meta(
    var mCode : Int,
    var mMesssage: String
)