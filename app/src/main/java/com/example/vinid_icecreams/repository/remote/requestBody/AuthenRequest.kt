package com.example.vinid_icecreams.repository.remote.requestBody

import com.google.gson.annotations.SerializedName

data class AuthenRequest (
    @SerializedName("phone_number")
    var phoneNumber : String,
    @SerializedName("password")
    var password : String
)