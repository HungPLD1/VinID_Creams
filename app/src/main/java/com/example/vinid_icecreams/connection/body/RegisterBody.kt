package com.example.vinid_icecreams.connection.body

import com.google.gson.annotations.SerializedName

data class RegisterBody (
    @SerializedName("phone_number")
    var phoneNumber : String,
    @SerializedName("password")
    var password : String
)